package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.LicenceType;

import java.util.List;

public interface LicenceTypeRepository extends JpaRepository<LicenceType, Long> {
    List<LicenceType> findByNameIgnoreCase(String name);
}
