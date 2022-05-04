package ru.otus.jdbc.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.core.repository.DataTemplate;
import ru.otus.jdbc.core.sessionmanager.TransactionAction;
import ru.otus.jdbc.core.sessionmanager.TransactionRunner;
import ru.otus.jdbc.crm.model.Manager;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.MetaDataAbstractFactory;

import java.util.List;
import java.util.Optional;

public class JdbcDBService<E> implements DBService<E> {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final MetaDataAbstractFactory<E> metaDataAbstractFactory;
    private final DBManager dbManager;
    private final DataTemplateJdbc<E> dataTemplate;

    public JdbcDBService(MetaDataAbstractFactory<E> metaDataAbstractFactory, DBManager dbManager) {
        this.metaDataAbstractFactory = metaDataAbstractFactory;
        this.dbManager = dbManager;
        this.dataTemplate = new DataTemplateJdbc<>(dbManager.getExecutor(), metaDataAbstractFactory);
    }

    @Override
    public E save(E entity) {
        return doInTransaction(connection -> {
            if (metaDataAbstractFactory.getEntityForJdbc().isIDNull(entity)) {
                var id = dataTemplate.insert(connection, entity);
                var createdManager = metaDataAbstractFactory.getEntityForJdbc().setID(id, entity);
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            dataTemplate.update(connection, entity);
            log.info("updated manager: {}", entity);
            return entity;
        });
    }

    @Override
    public Optional<E> findOne(long id) {
        return doInTransaction(connection -> {
            var managerOptional = dataTemplate.findById(connection, id);
            log.info("manager: {}", managerOptional);
            return managerOptional;
        });
    }

    @Override
    public List<E> findAll() {
        return doInTransaction(connection -> {
            var managerList = dataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
        });
    }

    private <T> T doInTransaction(TransactionAction<T> action) {
        return dbManager.getTransactionRunner().doInTransaction(action);
    }

}
