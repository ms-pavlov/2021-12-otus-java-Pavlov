package ru.otus.jdbc.crm.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HomeWork;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.mapper.EntityMetaDataAbstractFactory;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class JdbcDBServiceTest {
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final int COUNT = 100;
    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);
    private DBService<Client> service;

    @BeforeEach
    void before() {
        var dbManager = initDBManager();
        var clientMetaDataAbstractFactory = new EntityMetaDataAbstractFactory<>(Client.class, ReflectionMappingStrategy::new);
        service = new JdbcDBService<>(clientMetaDataAbstractFactory, dbManager);
    }


    @Test
    void saveAndFindOne() {
        var client = service.save(new Client("Vasa"));

        assertNotNull(client.getId());
        var id = client.getId();

        var inDBClient = service.findOne(id).orElse(null);

        assertEquals(client.getId(), inDBClient.getId());
        assertEquals(client.getName(), inDBClient.getName());

        var inDBClient2 = service.save(new Client(id, "Kolya"));

        assertEquals(client.getId(), inDBClient2.getId());
        assertNotEquals(client.getName(), inDBClient2.getName());

    }

    @Test
    void findAll() {
        for(long i = 0; i< COUNT; i++ ) {
            service.save(new Client("Client" + (i+1)));
        }
        var clients = service.findAll();
        clients.forEach(client -> assertEquals("Client" + client.getId(), client.getName()));
        assertEquals(COUNT, clients.size());
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