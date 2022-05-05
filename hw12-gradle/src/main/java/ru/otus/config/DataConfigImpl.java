package ru.otus.config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.service.DBManager;
import ru.otus.jdbc.crm.service.JdbcDBManager;
import ru.otus.web.dao.InMemoryUserDao;
import ru.otus.web.dao.UserDao;

import javax.sql.DataSource;

public class DataConfigImpl implements DataConfig {
    private static final String MIGRATION_CLASSPATH = "classpath:/db/migration";

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    private final DBManager dbClientManager;
    private final UserDao userDao;

    public DataConfigImpl(String url, String user, String password) {
        var dataSource = new DriverManagerDataSource(url, user, password);
        flywayMigrations(dataSource);
        this.dbClientManager = new  JdbcDBManager(dataSource);
        this.userDao = new InMemoryUserDao();
    }

    @Override
    public DBManager getDbClientManager() {
        return dbClientManager;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure().dataSource(dataSource).locations(MIGRATION_CLASSPATH).load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
