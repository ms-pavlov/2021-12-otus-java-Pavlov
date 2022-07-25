package ru.otus.services.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.client.request.SimpleWebRequestFactory;
import ru.otus.dto.requests.RoomsRequest;

@Component
public class RoomsWebRequestFactory extends SimpleWebRequestFactory<RoomsRequest> {
    public RoomsWebRequestFactory(@Value("${rooms.api.url}")String apiUpl) {
        super(apiUpl);
    }
}
