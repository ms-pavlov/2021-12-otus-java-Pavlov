package ru.otus.services.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.dto.request.ClientRequestDto;

@Component
public class ClientWebRequestFactory implements WebRequestFactory<ClientRequestDto> {
    private final String requestGetUrl;
    private final String requestPostUrl;
    private final String requestPutUrl;

    public ClientWebRequestFactory(
            @Value("${url.get}") String requestGetUrl,
            @Value("${url.post}") String requestPostUrl,
            @Value("${url.put}") String requestPutUrl) {
        this.requestGetUrl = requestGetUrl;
        this.requestPostUrl = requestPostUrl;
        this.requestPutUrl = requestPutUrl;
    }

    @Override
    public WebRequest<ClientRequestDto> prepGetRequest() {
        return new ClientWebRequest(requestGetUrl);
    }

    @Override
    public WebRequest<ClientRequestDto> prepPostRequest(ClientRequestDto request) {
        return new ClientWebRequest(requestPostUrl, request);
    }

    @Override
    public WebRequest<ClientRequestDto> prepPutRequest(Long id, ClientRequestDto request) {
        return new ClientWebRequest(String.format(requestPutUrl, id), request);
    }
}
