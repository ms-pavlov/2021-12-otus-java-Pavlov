package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Contacts;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}
