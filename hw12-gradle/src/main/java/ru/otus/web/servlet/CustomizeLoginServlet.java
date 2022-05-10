package ru.otus.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.config.WebServerConfig;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.UserAuthService;

import java.io.IOException;
import java.util.Collections;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class CustomizeLoginServlet extends HttpServlet {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final int MAX_INACTIVE_INTERVAL = 30;
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    private final TemplateProcessor templateProcessor;
    private final UserAuthService userAuthService;

    public CustomizeLoginServlet(WebServerConfig webServerConfig, UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
        this.templateProcessor = webServerConfig.getTemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request: {}", request);
        String name = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            response.sendRedirect("/clients");
        } else {
            response.setStatus(SC_UNAUTHORIZED);
        }

    }
}
