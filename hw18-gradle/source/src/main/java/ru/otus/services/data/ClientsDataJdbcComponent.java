package ru.otus.services.data;

import org.springframework.stereotype.Component;
import ru.otus.entities.Client;
import ru.otus.repositories.ClientRepository;
import ru.otus.services.data.sessionmanager.TransactionManager;

@Component
public class ClientsDataJdbcComponent extends SpringDataJdbcService<Client> {
    public ClientsDataJdbcComponent(TransactionManager transactionManager, ClientRepository crudRepository) {
        super(transactionManager, crudRepository);
    }
}
