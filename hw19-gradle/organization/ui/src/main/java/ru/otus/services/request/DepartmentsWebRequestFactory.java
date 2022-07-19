package ru.otus.services.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.client.request.SimpleWebRequestFactory;
import ru.otus.dto.requests.DepartmentsRequest;

@Component
public class DepartmentsWebRequestFactory extends SimpleWebRequestFactory<DepartmentsRequest> {
    public DepartmentsWebRequestFactory(@Value("${departments.api.url}") String apiUpl) {
        super(apiUpl);
    }
}
