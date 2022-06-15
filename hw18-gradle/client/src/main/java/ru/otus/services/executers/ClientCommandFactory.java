package ru.otus.services.executers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.otus.dto.request.ClientRequestDto;

@Component
public class ClientCommandFactory implements WebCommandFactory<ClientRequestDto> {

    @Override
    public WebCommand<ClientRequestDto> prepGet() {
        return (client, webResponse) -> client.get()
                .uri(webResponse.getUrl())
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve();
    }

    @Override
    public WebCommand<ClientRequestDto> prepPost() {
        return (client, webResponse) -> client.post()
                .uri(webResponse.getUrl())
                .accept(MediaType.APPLICATION_NDJSON)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }

    @Override
    public WebCommand<ClientRequestDto> prepPut() {
        return (client, webResponse) -> client.put()
                .uri(webResponse.getUrl())
                .accept(MediaType.APPLICATION_NDJSON)
                .body(webResponse.getBodyInserter())
                .retrieve();
    }
}
