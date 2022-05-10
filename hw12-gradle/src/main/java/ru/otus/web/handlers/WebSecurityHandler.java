package ru.otus.web.handlers;

import org.eclipse.jetty.server.Handler;
import ru.otus.config.WebServerConfig;

@FunctionalInterface
public interface WebSecurityHandler {
    Handler applySecurityHandler(WebServerConfig webServerConfig);
}
