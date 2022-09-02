package ru.otus.client.executers;


import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.request.WebRequest;

@FunctionalInterface
public interface WebCommand<Q> {
    WebClient.ResponseSpec execute(WebClient client, WebRequest<Q> webResponse);
}
