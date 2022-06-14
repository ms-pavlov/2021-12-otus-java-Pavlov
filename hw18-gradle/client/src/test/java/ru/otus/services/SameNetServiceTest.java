package ru.otus.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.dto.response.ClientResponseDto;
import ru.otus.services.executers.WebCommand;
import ru.otus.services.executers.WebCommandFactory;
import ru.otus.services.request.WebRequest;
import ru.otus.services.request.WebRequestFactory;

import static org.mockito.Mockito.*;


class SameNetServiceTest {

    private WebClient client;
    private WebCommandFactory<ClientRequestDto> commandFactory;
    private WebRequestFactory<ClientRequestDto> requestFactory;
    private WebCommand<ClientRequestDto> command;
    private WebRequest<ClientRequestDto> request;
    private WebClient.ResponseSpec responseSpec;

    private SameNetService<ClientResponseDto, ClientRequestDto> netService;
    private static final ClientRequestDto REQUEST_DTO = new ClientRequestDto("Vasa", 1);

    @BeforeEach
    void setUp() {
        commandFactory = mock(WebCommandFactory.class);
        requestFactory = mock(WebRequestFactory.class);
        command = mock(WebCommand.class);
        request = mock(WebRequest.class);
        responseSpec = mock(WebClient.ResponseSpec.class);
        netService = new SameNetService<>(client, commandFactory, requestFactory);
    }

    @Test
    void findAll() {
        when(requestFactory.prepGetRequest()).thenReturn(request);
        when(command.execute(client, request)).thenReturn(responseSpec);
        when(commandFactory.prepGet()).thenReturn(command);
        when(requestFactory.prepGetRequest()).thenReturn(request);
        netService.findAll();
        verify(commandFactory, times(1)).prepGet();
        verify(requestFactory, times(1)).prepGetRequest();
    }

    @Test
    void create() {
        when(requestFactory.prepPostRequest(REQUEST_DTO)).thenReturn(request);
        when(command.execute(client, request)).thenReturn(responseSpec);
        when(commandFactory.prepPost()).thenReturn(command);
        when(requestFactory.prepPostRequest(REQUEST_DTO)).thenReturn(request);
        netService.create(REQUEST_DTO);
        verify(commandFactory, times(1)).prepPost();
        verify(requestFactory, times(1)).prepPostRequest(REQUEST_DTO);
    }

    @Test
    void update() {
        when(requestFactory.prepPutRequest(1L, REQUEST_DTO)).thenReturn(request);
        when(command.execute(client, request)).thenReturn(responseSpec);
        when(commandFactory.prepPut()).thenReturn(command);
        when(requestFactory.prepPutRequest(1L, REQUEST_DTO)).thenReturn(request);
        netService.update(1L, REQUEST_DTO);
        verify(commandFactory, times(1)).prepPut();
        verify(requestFactory, times(1)).prepPutRequest(1L, REQUEST_DTO);
    }
}