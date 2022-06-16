package ru.otus.services.executers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.otus.dto.request.ClientRequestDto;

@Component
public class ClientCommandFactory implements WebCommandFactory<ClientRequestDto> {
    private final static MediaType MEDIA_TYPE = MediaType.APPLICATION_NDJSON;

    @Override
    public WebCommand<ClientRequestDto> prepGet() {
        return (client, webResponse) -> client.get()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .retrieve();
    }

    @Override
    public WebCommand<ClientRequestDto> prepPost() {
        return (client, webResponse) -> client.post()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }

    @Override
    public WebCommand<ClientRequestDto> prepPut() {
        return (client, webResponse) -> client.put()
                .uri(webResponse.getUrl())
                .accept(MEDIA_TYPE)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }
}
