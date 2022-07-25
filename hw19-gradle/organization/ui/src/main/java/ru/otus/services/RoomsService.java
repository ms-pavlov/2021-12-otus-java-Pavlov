package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.SameNetService;
import ru.otus.client.executers.WebCommandFactory;
import ru.otus.client.request.WebRequestFactory;
import ru.otus.dto.requests.RoomsRequest;
import ru.otus.dto.responses.RoomsResponse;

@Service
public class RoomsService extends SameNetService<RoomsResponse, RoomsRequest> {
    public RoomsService(WebClient customWebClient,
                        WebCommandFactory<RoomsRequest> commandFactory,
                        WebRequestFactory<RoomsRequest> requestFactory) {
        super(customWebClient, commandFactory, requestFactory);
    }
}
