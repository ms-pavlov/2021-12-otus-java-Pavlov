package ru.otus.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.config.WebServerConfig;
import ru.otus.web.services.UserAuthService;

import java.io.IOException;

public class CustomizeLoginServlet extends LoginServlet {
    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    public CustomizeLoginServlet(WebServerConfig webServerConfig, UserAuthService userAuthService) {
        super(webServerConfig.getTemplateProcessor(), userAuthService);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request: {}, response: {}", request, response);
        super.doPost(request, response);
    }
}
