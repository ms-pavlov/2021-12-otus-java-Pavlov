package ru.otus.adapter;

import org.springframework.web.reactive.function.client.WebClient;

public interface RequestExecutor {
    WebClient.ResponseSpec prepRequest(String url);
}
