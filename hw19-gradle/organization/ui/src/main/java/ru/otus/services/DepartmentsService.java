package ru.otus.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.client.SameNetService;
import ru.otus.client.executers.WebCommandFactory;
import ru.otus.client.request.WebRequestFactory;
import ru.otus.dto.requests.DepartmentsRequest;
import ru.otus.dto.responses.DepartmentsResponse;

@Service
public class DepartmentsService extends SameNetService<DepartmentsResponse, DepartmentsRequest> {
    public DepartmentsService(WebClient customWebClient,
                              WebCommandFactory<DepartmentsRequest> commandFactory,
                              WebRequestFactory<DepartmentsRequest> requestFactory) {
        super(customWebClient, commandFactory, requestFactory);
    }
}
