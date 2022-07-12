package ru.otus.services.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import ru.otus.services.data.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpringDataJdbcService<E> implements DBService<E> {
    private static final Logger log = LoggerFactory.getLogger(SpringDataJdbcService.class);

    private final CrudRepository<E, Long> crudRepository;
    private final TransactionManager transactionManager;

    public SpringDataJdbcService(TransactionManager transactionManager, CrudRepository<E, Long> crudRepository) {
        this.crudRepository = crudRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public E save(E entity) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = crudRepository.save(entity);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<E> findOne(long id) {
        var entityOptional = crudRepository.findById(id);
        log.info("client: {}", entityOptional);
        return entityOptional;
    }

    @Override
    public List<E> findAll() {
        var entitiesList = new ArrayList<E>();
        crudRepository.findAll().forEach(entitiesList::add);
        log.info("clientList:{}", entitiesList);
        return entitiesList;
    }

}
