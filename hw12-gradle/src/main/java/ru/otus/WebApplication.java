package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.config.DataConfig;
import ru.otus.config.DataConfigImpl;
import ru.otus.config.WebServerConfig;
import ru.otus.config.WebServerConfigImpl;
import ru.otus.web.server.WebServer;
import ru.otus.web.server.WebServerSimple;
import ru.otus.web.services.UserAuthServiceImpl;
import ru.otus.web.servlet.AuthorizationFilter;
import ru.otus.web.servlet.CustomizeLoginServlet;
import ru.otus.web.servlet.UsersApiServlet;
import ru.otus.web.servlet.UsersServlet;

public class WebApplication {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final int WEB_SERVER_PORT = 8080;

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) throws Exception {
        DataConfig dataConfig = new DataConfigImpl(URL, USER, PASSWORD);
        WebServerConfig serverConfig = new WebServerConfigImpl(webServerConfig -> initServlets(webServerConfig, dataConfig),
                (webServerConfig) -> initSecurity(webServerConfig, dataConfig));

        WebServer server = new WebServerSimple(WEB_SERVER_PORT, serverConfig);

        server.start();
        server.join();
    }

    private static ServletContextHandler initSecurity(WebServerConfig webServerConfig, DataConfig dataConfig) {
        webServerConfig.getServletContextHandler()
                .addServlet(new ServletHolder(new CustomizeLoginServlet(webServerConfig, new UserAuthServiceImpl(dataConfig.getUserDao()))), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        webServerConfig.getPaths().forEach(path -> webServerConfig.getServletContextHandler().addFilter(new FilterHolder(authorizationFilter), path, null));
        return webServerConfig.getServletContextHandler();
    }

    private static void initServlets(WebServerConfig webServerConfig, DataConfig dataConfig) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        webServerConfig.putServlet("/users", new UsersServlet(webServerConfig.getTemplateProcessor(), dataConfig.getUserDao()));
        webServerConfig.putServlet("/api/user/*", new UsersApiServlet(dataConfig.getUserDao(), gson));
    }
}
