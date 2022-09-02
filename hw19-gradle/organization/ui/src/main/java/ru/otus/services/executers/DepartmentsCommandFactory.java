package ru.otus.services.executers;

import org.springframework.stereotype.Component;
import ru.otus.client.executers.SimpleCommandFactory;
import ru.otus.dto.requests.DepartmentsRequest;

@Component
public class DepartmentsCommandFactory extends SimpleCommandFactory<DepartmentsRequest> {
}
