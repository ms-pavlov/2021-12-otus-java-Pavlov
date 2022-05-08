package ru.otus.web.servlet;

import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.config.DataConfig;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ClientsApiServletTest {

    private static final Client[] DEFAULT_CLIENTS = {new Client(1L, "Vasa")};
    private static final List<Client> clients = new ArrayList<>(Arrays.asList(DEFAULT_CLIENTS));
    private static final Logger log = LoggerFactory.getLogger(ClientsApiServletTest.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DataConfig data;
    private ServletOutputStream out;
    private DBService<Client> dbService;

    @BeforeEach
    void before() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        out = mock(ServletOutputStream.class);
        given(response.getOutputStream()).willReturn(out);

        dbService = (DBService<Client>) mock(DBService.class);
        given(dbService.findAll()).willReturn(clients);

        data = mock(DataConfig.class);
        given(data.getDbClientService()).willReturn(dbService);
        given(data.getGson()).willReturn(new GsonBuilder().serializeNulls().setPrettyPrinting().create());
    }

    @Test
    void doGet() throws IOException {
        new ClientsApiServlet(data).doGet(request, response);
        verify(out, times(1)).print(data.getGson().toJson(clients));
    }

    @Test
    void doPost() throws IOException {
        when(request.getParameter("name")).thenReturn("Kola");
        new ClientsApiServlet(data).doPost(request, response);
        verify(dbService, times(1)).save(new Client("Kola"));
    }
}