package ru.otus.services.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.client.request.SimpleWebRequestFactory;
import ru.otus.dto.requests.BuildingsRequest;

@Component
public class BuildingsWebRequestFactory extends SimpleWebRequestFactory<BuildingsRequest> {
    public BuildingsWebRequestFactory(@Value("${buildings.api.url}") String apiUpl) {
        super(apiUpl);
    }
}
