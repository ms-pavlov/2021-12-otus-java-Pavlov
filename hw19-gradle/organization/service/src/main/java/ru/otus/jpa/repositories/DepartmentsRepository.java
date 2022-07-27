package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Departments;

import java.util.List;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    @Override
    @EntityGraph("Departments.default")
    List<Departments> findAll();
}
