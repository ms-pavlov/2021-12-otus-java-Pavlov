package ru.otus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBService;
import ru.otus.jdbc.crm.service.JdbcDBManager;
import ru.otus.jdbc.crm.service.JdbcDBService;
import ru.otus.jdbc.mapper.EntityMetaDataAbstractFactory;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;
import ru.otus.web.dao.InMemoryUserDao;
import ru.otus.web.dao.UserDao;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class DataConfigImpl implements DataConfig {
    private static final String APPLICATION_PROPERTIES = "application";
    private static final String MIGRATION_CLASSPATH = ResourceBundle.getBundle(APPLICATION_PROPERTIES).getString("db.migration");

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    private final DBService<Client> dbClientService;
    private final UserDao userDao;
    private final Gson gson;

    public DataConfigImpl(String url, String user, String password) {
        var dataSource = new DriverManagerDataSource(url, user, password);
        flywayMigrations(dataSource);
        this.dbClientService = new JdbcDBService<>(new EntityMetaDataAbstractFactory<>(Client.class, ReflectionMappingStrategy::new), new JdbcDBManager(dataSource));
        this.userDao = new InMemoryUserDao();
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure().dataSource(dataSource).locations(MIGRATION_CLASSPATH).load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }

    @Override
    public DBService<Client> getDbClientService() {
        return dbClientService;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public Gson getGson() {
        return gson;
    }
}
