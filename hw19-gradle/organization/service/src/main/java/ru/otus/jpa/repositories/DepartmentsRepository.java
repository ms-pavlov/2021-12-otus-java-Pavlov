package ru.otus.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.jpa.entities.Departments;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
}
