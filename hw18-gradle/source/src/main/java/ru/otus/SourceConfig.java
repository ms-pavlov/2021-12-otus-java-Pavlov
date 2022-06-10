package ru.otus;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class SourceConfig {
    private final static int EVENT_LOOP_THREAD_POOL_DEFAULT_SIZE = 2;
    private final static int DATA_SOURCE_THREAD_POOL_DEFAULT_SIZE = 4;
    private final int eventLoopThreadPoolSize;
    private final int dataSourceExecutorThreadPoolSize;

    public SourceConfig(@Value("${eventLoopThreadPoolSize}") int eventLoopThreadPoolSize, @Value("${dataSourceExecutorThreadPoolSize}") int dataSourceExecutorThreadPoolSize) {
        this.eventLoopThreadPoolSize = (0 < eventLoopThreadPoolSize) ?
                eventLoopThreadPoolSize : EVENT_LOOP_THREAD_POOL_DEFAULT_SIZE;
        this.dataSourceExecutorThreadPoolSize = (0 < dataSourceExecutorThreadPoolSize) ?
                dataSourceExecutorThreadPoolSize : DATA_SOURCE_THREAD_POOL_DEFAULT_SIZE;
    }


    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        var eventLoopGroup = new NioEventLoopGroup(eventLoopThreadPoolSize,
                new ThreadFactory() {
                    private final AtomicLong threadIdGenerator = new AtomicLong(0);

                    @Override
                    public Thread newThread(@NonNull Runnable task) {
                        var thread = new Thread(task);
                        thread.setName("eventLoop-thread-" + threadIdGenerator.incrementAndGet());
                        return thread;
                    }
                });

        var factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder -> builder.runOn(eventLoopGroup));

        return factory;
    }

    @Bean
    public ExecutorService getDataSourceExecutor() {
        return Executors.newFixedThreadPool(dataSourceExecutorThreadPoolSize);
    }
}
