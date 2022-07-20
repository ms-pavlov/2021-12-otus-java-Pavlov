package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.SameNetService;
import ru.otus.client.executers.WebCommandFactory;
import ru.otus.client.request.WebRequestFactory;
import ru.otus.dto.requests.PlacementsRequest;
import ru.otus.dto.responses.PlacementsResponse;

@Service
public class PlacementsService extends SameNetService<PlacementsResponse, PlacementsRequest> {
    public PlacementsService(WebClient customWebClient,
                             WebCommandFactory<PlacementsRequest> commandFactory,
                             WebRequestFactory<PlacementsRequest> requestFactory) {
        super(customWebClient, commandFactory, requestFactory);
    }
}
