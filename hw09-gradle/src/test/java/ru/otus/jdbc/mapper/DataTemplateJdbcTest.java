package ru.otus.jdbc.mapper;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;

import javax.sql.DataSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class DataTemplateJdbcTest {
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbcTest.class);

    private TransactionRunnerJdbc transactionRunner;
    private DataTemplateJdbc<Client> dataTemplateClient;
    private DbServiceClientImpl dbServiceClient;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        this.transactionRunner = new TransactionRunnerJdbc(dataSource);

        var dbExecutor = new DbExecutorImpl();
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var clientMetaDataAbstractFactory = new EntityMetaDataAbstractFactory<>(Client.class, ReflectionMappingStrategy::new);
        this.dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, clientMetaDataAbstractFactory);

        this.dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
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

    @Test
    void findById() {
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        assertEquals(clientSecond.getId(), clientSecondSelected.getId());
    }

    @Test
    void findAll() {
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var client = dbServiceClient.saveClient(new Client("dbServiceSecond"));

        var list = transactionRunner.doInTransaction(connection -> {
            log.info("try findAll");
            var clientOptional = dataTemplateClient.findAll(connection);
            log.info("findAll: {}", clientOptional);
            return clientOptional;
        });
        log.info("findAll contains: {}", client);
        assertTrue(list.stream().anyMatch(client1 -> Objects.equals(client.getId(), client1.getId())));
    }

    @Test
    void insert() {
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        assertEquals(clientSecond.getId(), clientSecondSelected.getId());
    }

    @Test
    void update() {
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var client = dbServiceClient.saveClient(new Client("dbServiceSecond"));

        transactionRunner.doInTransaction(connection -> {
            dataTemplateClient.update(connection, new Client(client.getId(), "update"));
            log.info("update: {}", client.getId());
            return null;
        });
        var clientUpdated = dbServiceClient.getClient(client.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + client.getId()));
        log.info("findAll contains: {}", client);
        assertEquals("update", clientUpdated.getName());
    }
}