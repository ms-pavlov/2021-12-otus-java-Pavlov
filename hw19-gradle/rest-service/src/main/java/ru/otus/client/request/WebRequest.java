package ru.otus.client.request;

import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;

public interface WebRequest<Q> {
    String getUrl();

    BodyInserter<Q, ReactiveHttpOutputMessage> getBodyInserter();
}
