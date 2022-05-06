package ru.otus.jdbc.crm.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class JdbcDBManagerTest {
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final Logger log = LoggerFactory.getLogger(JdbcDBManagerTest.class);
    private JdbcDBManager dbManager;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);

        dbManager = new JdbcDBManager(dataSource);
    }

    private static void flywayMigrations(DataSource dataSource) {
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
    }

    @Test
    void getTransactionRunner() {
        assertNotNull(dbManager.getTransactionRunner());
    }

    @Test
    void getExecutor() {
        assertNotNull(dbManager.getExecutor());
    }
}