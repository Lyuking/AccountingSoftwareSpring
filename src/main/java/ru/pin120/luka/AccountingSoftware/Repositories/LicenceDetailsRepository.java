package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.LicenceDetails;

import java.util.List;

public interface LicenceDetailsRepository extends JpaRepository<LicenceDetails, Long> {
    List<LicenceDetails> findByLicenceKeyIgnoreCase(String key);
}
