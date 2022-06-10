package ru.otus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.entities.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
