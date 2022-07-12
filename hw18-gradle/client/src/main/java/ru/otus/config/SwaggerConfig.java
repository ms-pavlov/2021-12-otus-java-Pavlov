package ru.otus.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi(@Value("${application.api.prefix}") String prefix) {
        return GroupedOpenApi.builder()
                .group("DemoApplication")
                .pathsToMatch(String.format("%s/**", prefix))
                .build();
    }
}
