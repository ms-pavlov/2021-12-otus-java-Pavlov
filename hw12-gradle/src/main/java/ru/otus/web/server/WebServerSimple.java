package ru.otus.web.server;

import org.eclipse.jetty.server.Server;
import ru.otus.config.WebServerConfig;

public class WebServerSimple implements WebServer{
    private final Server server;
    private final WebServerConfig serverConfig;

    public WebServerSimple(int port, WebServerConfig serverConfig) {
        server = new Server(port);
        this.serverConfig = serverConfig;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            server.setHandler(serverConfig.getHandlers());
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

}
