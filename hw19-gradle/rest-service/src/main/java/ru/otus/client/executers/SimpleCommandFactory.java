package ru.otus.client.executers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class SimpleCommandFactory<Q> implements WebCommandFactory<Q> {
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;

    @Override
    public WebCommand<Q> prepGet() {
        return (client, webResponse) -> client.get()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .retrieve();
    }

    @Override
    public WebCommand<Q> prepPost() {
        return (client, webResponse) -> client.post()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }

    @Override
    public WebCommand<Q> prepPut() {
        return (client, webResponse) -> client.put()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }
}
