package ru.otus.services.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientWebRequestFactoryTest {
    private static final String URL1 = "/url1/";
    private static final String URL2 = "/url2/";
    private static final String URL3 = "/url3/%d/";
    private ClientWebRequestFactory requestFactory;

    @BeforeEach
    void setUp() {
        requestFactory = new ClientWebRequestFactory(URL1, URL2, URL3);
    }

    @Test
    void prepGetRequest() {
        assertEquals(URL1, requestFactory.prepGetRequest().getUrl());
    }

    @Test
    void prepPostRequest() {
        assertEquals(URL2, requestFactory.prepPostRequest(null).getUrl());
    }

    @Test
    void prepPutRequest() {
        assertEquals(String.format(URL3, 1L), requestFactory.prepPutRequest(1L, null).getUrl());
    }

}