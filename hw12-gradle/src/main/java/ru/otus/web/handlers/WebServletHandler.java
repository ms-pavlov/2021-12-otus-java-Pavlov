package ru.otus.web.handlers;

import ru.otus.config.WebServerConfig;

@FunctionalInterface
public interface WebServletHandler {
    void initServletContextHandler(WebServerConfig webServerConfig);
}
