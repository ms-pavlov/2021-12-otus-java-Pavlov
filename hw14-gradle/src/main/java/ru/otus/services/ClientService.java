package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;
import ru.otus.entities.Client;
import ru.otus.models.ClientModel;
import ru.otus.services.data.ClientsDataJdbcComponent;

import java.util.Comparator;
import java.util.List;

@Service
public class ClientService {

    private final ClientsDataJdbcComponent clientsData;

    public ClientService(ClientsDataJdbcComponent clientsData) {
        this.clientsData = clientsData;
    }

    public List<ClientResponse> findAll() {
        var clientModels = clientsData.findAll().stream()
                .map(ClientModel::new).toList();

        return clientModels.stream()
                .sorted(Comparator.comparingInt(ClientModel::getOrder))
                .map(ClientResponse::new).toList();
    }

    public ClientResponse create(ClientRequest clientRequest) {
        var client = clientsData.save(new ClientModel(null, clientRequest).toClient());
        return new ClientResponse(new ClientModel(client));
    }
    public ClientResponse update(Long id, ClientRequest clientRequest) {
        var client = clientsData.save(new ClientModel(id, clientRequest).toClient());
        return new ClientResponse(new ClientModel(client));
    }
}
