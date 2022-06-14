package ru.otus.services.request;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.dto.request.ClientRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ClientWebRequestTest {
    private static final Logger log = LoggerFactory.getLogger(ClientWebRequestTest.class);
    private static final String URL = "/url/";
    private static final ClientRequestDto CLIENT_REQUEST = new ClientRequestDto("Vasya", 1);

    @Test
    void getUrl() {
        var request = new ClientWebRequest(URL, null);

        assertEquals(URL, request.getUrl());
    }

    @Test
    void getBodyInserter() {
        var request = new ClientWebRequest(URL);
        assertEquals(BodyInserters.empty(), request.getBodyInserter());

        request = new ClientWebRequest(URL, CLIENT_REQUEST);
        assertNotEquals(BodyInserters.empty(),request.getBodyInserter());
    }
}