package ru.otus.config;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.web.services.TemplateProcessor;

import java.util.Set;

public interface WebServerConfig {

    Set<String> getPaths();

    TemplateProcessor getTemplateProcessor();

    void putServlet(String path, HttpServlet servlet);

    ServletContextHandler getServletContextHandler();

    HandlerList getHandlers();
}
