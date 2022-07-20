package ru.otus.client;

import org.springframework.web.reactive.function.client.WebClient;

@FunctionalInterface
public interface CustomWebClient {
    WebClient getWebClient();
}
