package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBManager;
import ru.otus.jdbc.crm.service.JdbcDBManager;
import ru.otus.jdbc.crm.service.JdbcDBService;
import ru.otus.jdbc.mapper.EntityMetaDataAbstractFactory;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;
import ru.otus.web.dao.InMemoryUserDao;
import ru.otus.web.dao.UserDao;
import ru.otus.web.server.UsersWebServer;
import ru.otus.web.server.UsersWebServerWithFilterBasedSecurity;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.TemplateProcessorImpl;
import ru.otus.web.services.UserAuthService;
import ru.otus.web.services.UserAuthServiceImpl;

import javax.sql.DataSource;
import java.io.IOException;

public class WebApplication {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) throws Exception {
        var dbManager = initDBManager();
        var clientService = new JdbcDBService<>(new EntityMetaDataAbstractFactory<>(Client.class, ReflectionMappingStrategy::new), dbManager);

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static DBManager initDBManager() {
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        return new JdbcDBManager(dataSource);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
