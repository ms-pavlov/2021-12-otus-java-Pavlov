package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.core.repository.executor.DbExecutorImpl;
import ru.otus.jdbc.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.model.Manager;
import ru.otus.jdbc.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.crm.service.DbServiceManagerImpl;
import ru.otus.jdbc.mapper.*;
import ru.otus.jdbc.mapper.strategy.ReflectionMappingStrategy;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        var clientMetaDataAbstractFactory = new EntityMetaDataAbstractFactory<>(Client.class, ReflectionMappingStrategy::new);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, clientMetaDataAbstractFactory); //реализация DataTemplate, универсальная

// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)
        var managerMetaDataAbstractFactory = new EntityMetaDataAbstractFactory<>(Manager.class, ReflectionMappingStrategy::new);
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, managerMetaDataAbstractFactory);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);
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
