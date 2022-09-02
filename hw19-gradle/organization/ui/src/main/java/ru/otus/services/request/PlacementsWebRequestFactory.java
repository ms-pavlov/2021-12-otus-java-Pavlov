package ru.otus.services.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.client.request.SimpleWebRequestFactory;
import ru.otus.dto.requests.PlacementsRequest;

@Component
public class PlacementsWebRequestFactory extends SimpleWebRequestFactory<PlacementsRequest> {
    public PlacementsWebRequestFactory(@Value("${placements.api.url}") String apiUpl) {
        super(apiUpl);
    }
}
