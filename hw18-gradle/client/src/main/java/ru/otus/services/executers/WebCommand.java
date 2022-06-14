package ru.otus.services.executers;

import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.services.request.WebRequest;

@FunctionalInterface
public interface WebCommand<Q> {
    WebClient.ResponseSpec execute(WebClient client, WebRequest<Q> webResponse);
}
