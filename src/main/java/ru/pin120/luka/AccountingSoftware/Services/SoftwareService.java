package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Models.Software;
import ru.pin120.luka.AccountingSoftware.Repositories.SoftwareRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareService {
    private final SoftwareRepository softwareRepository;

    @Autowired
    public SoftwareService(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    // Метод для создания программного обеспечения
    public Software createSoftware(Software software) {
        return softwareRepository.save(software);
    }

    // Метод для получения программного обеспечения по ID
    public Software getSoftwareById(Long id) {
        Optional<Software> optionalSoftware = softwareRepository.findById(id);
        return optionalSoftware.orElse(null);
    }

    // Метод для обновления программного обеспечения
    public Software updateSoftware(Software software) {
        return softwareRepository.save(software);
    }

    // Метод для получения всего программного обеспечения
    public List<Software> getAllSoftware() {
        return (List<Software>) softwareRepository.findAll();
    }

    // Метод для удаления программного обеспечения по ID
    public void deleteSoftware(Long id) {
        Software software = softwareRepository.findById(id).orElse(null);
        if (software != null) {
            for (Computer computer : software.getComputers()) {
                computer.getSoftwares().remove(software);
            }
            softwareRepository.delete(software);
        }
    }
}
