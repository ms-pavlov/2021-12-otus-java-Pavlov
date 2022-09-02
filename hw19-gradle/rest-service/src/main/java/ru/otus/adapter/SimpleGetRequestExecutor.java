package ru.otus.adapter;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class SimpleGetRequestExecutor implements RequestExecutor {
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;
    private final WebClient customWebClient;

    public SimpleGetRequestExecutor(WebClient customWebClient) {
        this.customWebClient = customWebClient;
    }

    @Override
    public WebClient.ResponseSpec prepRequest(String url) {
        return customWebClient
                .get()
                .uri(url)
                .accept(MEDIA_TYPE)
                .retrieve();
    }
}
