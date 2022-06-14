package ru.otus;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ClientConfig {
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
        return new NioEventLoopGroup(poolSize,
                new ThreadFactory() {
                    private final AtomicLong threadIdGenerator = new AtomicLong(0);

                    @Override
                    public Thread newThread(@NonNull Runnable task) {
                        var thread = new Thread(task);
                        thread.setName(threadsName + threadIdGenerator.incrementAndGet());
                        return thread;
                    }
                });
    }

    @Bean
    public WebClient getCustomWebClient(@Value("${url.host}") String url, WebClient.Builder builder) {
        return builder.baseUrl(url).build();
    }

    @Bean
    public ExecutorService getDataSourceExecutor() {
        return Executors.newFixedThreadPool(dataSourceExecutorThreadPoolSize);
    }
}
