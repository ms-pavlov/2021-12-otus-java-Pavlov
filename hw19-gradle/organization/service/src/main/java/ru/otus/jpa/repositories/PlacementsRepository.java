package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Placements;

import java.util.List;

@Repository
public interface PlacementsRepository extends JpaRepository<Placements, Long> {
    @Override
    @EntityGraph("Placements.default")
    List<Placements> findAll();
}
