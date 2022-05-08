package ru.otus.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.config.DataConfig;
import ru.otus.config.WebServerConfig;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBService;
import ru.otus.web.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClientServlet extends HttpServlet {
    private static final String CLIENT_PAGE_TEMPLATE = "client.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private final DataConfig data;
    private final TemplateProcessor templateProcessor;

    private static final Logger log = LoggerFactory.getLogger(ClientServlet.class);

    public ClientServlet(WebServerConfig webServerConfig, DataConfig dataConfig) {
        this.data = dataConfig;
        this.templateProcessor = webServerConfig.getTemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        log.info("Find clients data");
        Map<String, Object> paramsMap = new HashMap<>();
        var clients = Optional.ofNullable(data.getDbClientService().findAll());
        clients.ifPresent(list -> paramsMap.put(TEMPLATE_ATTR_CLIENTS, data.getGson().toJson(list)));
        log.info("paramsMap: {}", paramsMap);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENT_PAGE_TEMPLATE, paramsMap));
    }


}
