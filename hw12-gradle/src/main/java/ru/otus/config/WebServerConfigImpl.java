package ru.otus.config;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.web.handlers.WebSecurityHandler;
import ru.otus.web.handlers.WebServletHandler;
import ru.otus.web.helpers.FileSystemHelper;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.TemplateProcessorImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class WebServerConfigImpl implements WebServerConfig {
    private static final String APPLICATION_PROPERTIES = "application";
    private static final String START_PAGE_NAME =  ResourceBundle.getBundle(APPLICATION_PROPERTIES).getString("web.startPageName");
    private static final String COMMON_RESOURCES_DIR = ResourceBundle.getBundle(APPLICATION_PROPERTIES).getString("web.commonResourcesDir");
    private static final String TEMPLATES_DIR = ResourceBundle.getBundle(APPLICATION_PROPERTIES).getString("web.templatesDir");

    private final Map<String, ServletHolder> servletHolders = new HashMap<>();
    private final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
    private final ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private final HandlerList handlers = new HandlerList();

    public WebServerConfigImpl(WebServletHandler servletHandler, WebSecurityHandler securityHandler) throws IOException {
        servletHandler.initServletContextHandler(this);
        this.servletHolders.forEach((path, servletHolder) -> servletContextHandler.addServlet(servletHolder, path));
        handlers.addHandler(createResourceHandler());
        handlers.addHandler(securityHandler.applySecurityHandler(this));
    }

    @Override
    public Set<String> getPaths() {
        return servletHolders.keySet();
    }

    @Override
    public TemplateProcessor getTemplateProcessor() {
        return templateProcessor;
    }

    @Override
    public void putServlet(String path, HttpServlet servlet) {
        servletHolders.put(path, new ServletHolder(servlet));
    }

    @Override
    public ServletContextHandler getServletContextHandler() {
        return servletContextHandler;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    private static ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }
}
