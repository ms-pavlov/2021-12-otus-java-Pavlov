package ru.otus.web.servlet;

import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.config.DataConfig;
import ru.otus.config.WebServerConfig;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBService;
import ru.otus.web.services.TemplateProcessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ClientServletTest {
    private static final Client[] DEFAULT_CLIENTS = {new Client(1L, "Vasa")};
    private static final List<Client> clients = new ArrayList<>(Arrays.asList(DEFAULT_CLIENTS));
    private static final Logger log = LoggerFactory.getLogger(ClientsApiServletTest.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DataConfig data;
    private TemplateProcessor templateProcessor;
    private WebServerConfig webServerConfig;

    @BeforeEach
    void before() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        given(response.getWriter()).willReturn(writer);

        webServerConfig = mock(WebServerConfig.class);
        templateProcessor = mock(TemplateProcessor.class);
        given(webServerConfig.getTemplateProcessor()).willReturn(templateProcessor);

        DBService<Client> dbService = (DBService<Client>) mock(DBService.class);
        given(dbService.findAll()).willReturn(clients);

        data = mock(DataConfig.class);
        given(data.getDbClientService()).willReturn(dbService);
        given(data.getGson()).willReturn(new GsonBuilder().serializeNulls().setPrettyPrinting().create());
    }
    @Test
    void doGet() throws IOException {
        new ClientServlet(webServerConfig, data).doGet(request, response);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("clients", data.getGson().toJson(clients));

        verify(templateProcessor, times(1)).getPage("client.html", paramsMap);
    }
}