package ru.otus.client.executers;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.request.WebRequest;

public class SimpleWebCommand<Q> implements WebCommand<Q>{
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;
    @Override
    public WebClient.ResponseSpec execute(WebClient client, WebRequest<Q> webResponse) {
        return client
                .get()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .retrieve();
    }
}
