package otus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ClientConfig {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static int EVENT_LOOP_THREAD_POOL_DEFAULT_SIZE = 2;
    private final static int DATA_SOURCE_THREAD_POOL_DEFAULT_SIZE = 4;
    private final int webServiceEventLoopThreadPoolSize;
    private final int dataSourceEventLoopThreadPoolSize;
    private final int dataSourceExecutorThreadPoolSize;

    public ClientConfig(@Value("${eventLoopThreadPoolSize.webService}") int webServiceEventLoopThreadPoolSize,
                        @Value("${eventLoopThreadPoolSize.dataSource}") int dataSourceEventLoopThreadPoolSize,
                        @Value("${executorThreadPoolSize.dataSource}") int dataSourceExecutorThreadPoolSize) {
        this.dataSourceEventLoopThreadPoolSize = (0 < dataSourceEventLoopThreadPoolSize) ?
                dataSourceEventLoopThreadPoolSize : EVENT_LOOP_THREAD_POOL_DEFAULT_SIZE;
        this.webServiceEventLoopThreadPoolSize = (0 < webServiceEventLoopThreadPoolSize) ?
                webServiceEventLoopThreadPoolSize : EVENT_LOOP_THREAD_POOL_DEFAULT_SIZE;
        this.dataSourceExecutorThreadPoolSize = (0 < dataSourceExecutorThreadPoolSize) ?
                dataSourceExecutorThreadPoolSize : DATA_SOURCE_THREAD_POOL_DEFAULT_SIZE;
    }

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        var factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder ->
                builder.runOn(prepNioEventLoopGroup(webServiceEventLoopThreadPoolSize, "server-thread-")));

        return factory;
    }

    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        var resourceFactory = new ReactorResourceFactory();
        resourceFactory.setLoopResources(b -> prepNioEventLoopGroup(dataSourceEventLoopThreadPoolSize, "client-thread-"));
        resourceFactory.setUseGlobalResources(false);
        return resourceFactory;
    }

    private NioEventLoopGroup prepNioEventLoopGroup(int poolSize, String threadsName) {
        return new NioEventLoopGroup(poolSize, new SimplerThreadFactory(threadsName));
    }

    @Bean
    public WebClient getCustomWebClient(@Value("${url.host}") String url, WebClient.Builder builder) {
        return builder.baseUrl(url).build();
    }

    @Bean
    public ExecutorService getDataSourceExecutor() {
        return Executors.newFixedThreadPool(dataSourceExecutorThreadPoolSize, new SimplerThreadFactory("executor-"));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

    @Bean
    Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper){
        return new Jackson2JsonEncoder(mapper);
    }

    @Bean
    Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper){
        return new Jackson2JsonDecoder(mapper);
    }

    @Bean
    WebFluxConfigurer webFluxConfigurer(Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder){
        return new WebFluxConfigurer() {
            @Override
            public void configureHttpMessageCodecs(@NonNull ServerCodecConfigurer configuration) {
                configuration.defaultCodecs().jackson2JsonEncoder(encoder);
                configuration.defaultCodecs().jackson2JsonDecoder(decoder);
            }
        };

    }
}
