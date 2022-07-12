package ru.otus.services.executers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.dto.request.ClientRequestDto;
import ru.otus.services.request.ClientWebRequestFactory;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;


class ClientCommandFactoryTest {
    private static final String URL = "/url/";
    private static final String URL_PUT = "/url/%d";
    private static final ClientRequestDto CLIENT_REQUEST = new ClientRequestDto("Vasa", 1);
    private static final TestResponse CLIENT_RESPONSE1 = new TestResponse(1L, "Vasa");
    private static final TestResponse CLIENT_RESPONSE2 = new TestResponse(2L, "Kolya");

    private static WebClient client;
    private static ClientWebRequestFactory requestFactory;
    private static ClientCommandFactory commandFactory;
    private static WireMockServer wireMockServer;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        client = WebClient.create("http://localhost:" + wireMockServer.port());

        requestFactory = new ClientWebRequestFactory(URL, URL, URL_PUT);
        commandFactory = new ClientCommandFactory();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void prepGet() throws JsonProcessingException {
        stubFor(get(urlEqualTo(requestFactory.prepGetRequest().getUrl()))
                .willReturn(aResponse().withFixedDelay(1)
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                mapper.writeValueAsString(List.of(CLIENT_RESPONSE1, CLIENT_RESPONSE2))
                        )));

        var result = commandFactory
                .prepGet()
                .execute(client, requestFactory.prepGetRequest())
                .bodyToFlux(TestResponse.class)
                .collectList()
                .block();

        assertNotNull(result);
        assertTrue(result.contains(CLIENT_RESPONSE1));
        assertTrue(result.contains(CLIENT_RESPONSE2));
    }

    @Test
    void prepPost() throws JsonProcessingException {
        stubFor(post(urlEqualTo(requestFactory.prepPostRequest(CLIENT_REQUEST).getUrl()))
                .willReturn(aResponse().withFixedDelay(1)
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                mapper.writeValueAsString(CLIENT_RESPONSE1)
                        )));

        var result = commandFactory
                .prepPost()
                .execute(client, requestFactory.prepPostRequest(CLIENT_REQUEST))
                .bodyToMono(TestResponse.class)
                .block();

        assertEquals(CLIENT_RESPONSE1, result);
    }

    @Test
    void prepPut() throws JsonProcessingException {
        stubFor(put(urlEqualTo(requestFactory.prepPutRequest(1L, CLIENT_REQUEST).getUrl()))
                .willReturn(aResponse().withFixedDelay(1)
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                mapper.writeValueAsString(CLIENT_RESPONSE1)
                        )));

        var result = commandFactory
                .prepPut()
                .execute(client, requestFactory.prepPutRequest(1L, CLIENT_REQUEST))
                .bodyToMono(TestResponse.class)
                .block();

        assertEquals(CLIENT_RESPONSE1, result);
    }
}