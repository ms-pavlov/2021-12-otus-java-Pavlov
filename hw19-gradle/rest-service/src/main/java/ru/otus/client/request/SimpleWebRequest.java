package ru.otus.client.request;

import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

public class SimpleWebRequest<Q> implements WebRequest<Q> {
    private final String url;
    private final Q request;

    public SimpleWebRequest(String url) {
        this.url = url;
        this.request = null;
    }

    public SimpleWebRequest(String url, Q request) {
        this.url = url;
        this.request = request;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public BodyInserter<Q, ReactiveHttpOutputMessage> getBodyInserter() {
        return Optional.ofNullable(request).map(BodyInserters::fromValue).orElse(BodyInserters.empty());
    }
}
