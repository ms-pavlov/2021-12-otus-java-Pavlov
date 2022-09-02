package ru.otus.services.executers;

import org.springframework.stereotype.Component;
import ru.otus.client.executers.SimpleCommandFactory;
import ru.otus.dto.requests.PlacementsRequest;

@Component
public class PlacementsCommandFactory extends SimpleCommandFactory<PlacementsRequest> {
}
