package ru.otus.services;

import org.springframework.web.reactive.function.client.WebClient;

@FunctionalInterface
public interface CustomWebClient {
    WebClient getWebClient();
}
