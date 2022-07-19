package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.SameNetService;
import ru.otus.client.executers.WebCommandFactory;
import ru.otus.client.request.WebRequestFactory;
import ru.otus.dto.requests.BuildingsRequest;
import ru.otus.dto.responses.BuildingsResponse;

@Service
public class BuildingsService extends SameNetService<BuildingsResponse, BuildingsRequest> {
    public BuildingsService(WebClient customWebClient, WebCommandFactory<BuildingsRequest> commandFactory, WebRequestFactory<BuildingsRequest> requestFactory) {
        super(customWebClient, commandFactory, requestFactory);
    }
}
