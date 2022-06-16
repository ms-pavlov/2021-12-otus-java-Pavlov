package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@EnableScheduling
@Configuration
public class ScheduleConfig {
    @Bean
    public Scheduler timer() {
        return Schedulers.newParallel("scheduler", 2);
    }
}
