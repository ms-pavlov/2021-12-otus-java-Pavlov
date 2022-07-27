package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Buildings;

import java.util.List;

@Repository
public interface BuildingsRepository extends JpaRepository<Buildings, Long> {
    @Override
    @EntityGraph("Buildings.default")
    List<Buildings> findAll();
}
