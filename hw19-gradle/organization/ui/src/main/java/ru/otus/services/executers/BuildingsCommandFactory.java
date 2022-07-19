package ru.otus.services.executers;

import org.springframework.stereotype.Component;
import ru.otus.client.executers.SimpleCommandFactory;
import ru.otus.dto.requests.BuildingsRequest;

@Component
public class BuildingsCommandFactory extends SimpleCommandFactory<BuildingsRequest> {
}
