package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.Software;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
}
