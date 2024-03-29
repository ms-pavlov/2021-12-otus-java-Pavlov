package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Rooms;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {
}
