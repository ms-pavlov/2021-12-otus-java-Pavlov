package ru.otus.services.request;

import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.dto.request.ClientRequestDto;

import java.util.Optional;

public class ClientWebRequest implements WebRequest<ClientRequestDto> {
    private final String url;
    private final ClientRequestDto request;

    public ClientWebRequest(String url) {
        this.url = url;
        this.request = null;
    }

    public ClientWebRequest(String url, ClientRequestDto request) {
        this.url = url;
        this.request = request;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public BodyInserter<ClientRequestDto, ReactiveHttpOutputMessage> getBodyInserter() {
        return Optional.ofNullable(request).map(BodyInserters::fromValue).orElse(BodyInserters.empty());
    }
}
