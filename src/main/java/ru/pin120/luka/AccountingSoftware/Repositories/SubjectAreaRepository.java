package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;

import java.util.List;

public interface SubjectAreaRepository extends JpaRepository<SubjectArea, Long> {
    List<SubjectArea> findByNameIgnoreCase(String name);
}
