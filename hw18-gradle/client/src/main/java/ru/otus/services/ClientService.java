package ru.otus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.executers.WebCommandFactory;
import ru.otus.services.request.WebRequestFactory;

@Service
public class ClientService extends SameNetService<ClientResponseDto, ClientRequestDto> {
    public ClientService(WebClient customWebClient,
                         WebCommandFactory<ClientRequestDto> commandFactory,
                         WebRequestFactory<ClientRequestDto> requestFactory) {
        super(customWebClient, commandFactory, requestFactory);
    }
}
