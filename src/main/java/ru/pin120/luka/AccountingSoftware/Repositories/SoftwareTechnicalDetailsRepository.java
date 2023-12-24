package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.SoftwareTechnicalDetails;

import java.util.List;

public interface SoftwareTechnicalDetailsRepository extends JpaRepository<SoftwareTechnicalDetails, Long> {
    List<SoftwareTechnicalDetails> findByNameIgnoreCase(String name);
}
