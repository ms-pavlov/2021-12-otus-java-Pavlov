package ru.otus.services;

import ru.otus.dto.response.ClientResponseDto;

import java.util.List;

public interface JdbcService<Res, Req> {
    List<ClientResponseDto> findAll();

    Res create(Req clientRequest);

    Res update(Long id, Req clientRequest);
}
