package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.models.ClientModel;
import ru.otus.services.data.ClientsDataJdbcComponent;

import java.util.Comparator;
import java.util.List;

@Service
public class ClientService implements JdbcService<ClientResponseDto, ClientRequestDto> {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientsDataJdbcComponent clientsData;

    public ClientService(ClientsDataJdbcComponent clientsData) {
        this.clientsData = clientsData;
    }

    @Override
    public List<ClientResponseDto> findAll() {
        var clientModels = clientsData.findAll().stream()
                .map(ClientModel::new)
                .sorted(Comparator.comparingInt(ClientModel::getOrder))
                .toList();
        return clientModels.stream()
                .map(ClientModel::toClientResponse)
                .toList();
    }

    @Override
    public ClientResponseDto create(ClientRequestDto clientRequest) {
        var client = clientsData.save(new ClientModel(null, clientRequest).toClient());
        return new ClientModel(client).toClientResponse();
    }

    @Override
    public ClientResponseDto update(Long id, ClientRequestDto clientRequest) {
        var client = clientsData.save(new ClientModel(id, clientRequest).toClient());
        return new ClientModel(client).toClientResponse();
    }
}
