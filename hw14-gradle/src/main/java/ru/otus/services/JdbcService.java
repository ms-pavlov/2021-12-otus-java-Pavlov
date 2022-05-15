package ru.otus.services;

import ru.otus.dto.request.ClientRequest;
import ru.otus.dto.response.ClientResponse;

import java.util.List;

public interface JdbcService <Res, Req>{
    List<ClientResponse> findAll();

    Res create(Req clientRequest);

    Res update(Long id, Req clientRequest);
}
