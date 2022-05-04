package ru.otus.jdbc.crm.service;

import org.flywaydb.core.Flyway;
import org.h2.util.MathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.cachehw.SimpleListenersStorage;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.mapper.*;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.cachehw.MyCacheTest.getAction;

class DbServiceClientWithCacheImplTest {
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final int COUNT = 1000;
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientWithCacheImplTest.class);
    private List<String> messages;

    private TransactionRunnerJdbc transactionRunner;
    private DataTemplateJdbc<Client> dataTemplateClient;
    private DbServiceClientImpl dbServiceClient;

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
        this.messages = new ArrayList<>();

        HwCache<Long, Client> clientCache = new MyCache<>(new SimpleListenersStorage<>());
        clientCache.addListener((key, value, action) -> {
            log.info("key:{}, value:{}, action: {}", key, value, action);
            this.messages.add(key + value.toString() + action);
        });

        this.dbServiceClient = new DbServiceClientWithCacheImpl(transactionRunner, dataTemplateClient, clientCache);
    }

    @Test
    void getClient() throws InterruptedException {
        for (long i = 0L; i < COUNT; i++) {
            dbServiceClient.saveClient(new Client("test name"));
        }
        System.gc();
        Thread.sleep(100);

        long id = MathUtils.randomInt(COUNT);
        long beginDB = System.nanoTime();
        var msg = getClient(id);
        log.info("get client from DB time {} sec", (System.nanoTime() - beginDB)/ 1_000_000_000.0  );
        assertFalse(messages.contains(msg));
        long beginCache = System.nanoTime();
        msg = getClient(id);
        log.info("get client from Cache time {} sec", (System.nanoTime() - beginCache)/ 1_000_000_000.0 );
        assertTrue(messages.contains(msg));
    }

    private String getClient(long id) {
        return dbServiceClient.getClient(id)
                .map(client -> client.getId() + client.toString() + getAction("get"))
                .orElse("DataBase Error");
    }

    @Test
    void saveClient() {
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var msg = clientSecond.getId() + clientSecond.toString() + getAction("put");
        assertEquals(msg, messages.get(0));
    }

    @Test
    void findAll() {
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        messages.clear();
        var clients = dbServiceClient.findAll();

        clients.forEach(client -> {
            var msg = client.getId() + client.toString() + getAction("put");
            assertTrue(messages.contains(msg));
        });
        assertEquals(1, messages.size());
    }
}