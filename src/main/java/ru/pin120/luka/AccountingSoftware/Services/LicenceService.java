package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Licence;
import ru.pin120.luka.AccountingSoftware.Repositories.LicenceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LicenceService {
    private final LicenceRepository licenceRepository;

    @Autowired
    public LicenceService(LicenceRepository licenceRepository) {
        this.licenceRepository = licenceRepository;
    }

    // Метод для создания лицензии
    public Licence createLicence(Licence licence) {
        return licenceRepository.save(licence);
    }

    // Метод для получения лицензии по ID
    public Licence getLicenceById(Long id) {
        Optional<Licence> optionalLicence = licenceRepository.findById(id);
        return optionalLicence.orElse(null);
    }

    // Метод для обновления лицензии
    public Licence updateLicence(Licence licence) {
        return licenceRepository.save(licence);
    }

    // Метод для получения всех лицензий
    public List<Licence> getAllLicences() {
        return (List<Licence>) licenceRepository.findAll();
    }

    // Метод для удаления лицензии по ID
    public void deleteLicence(Long id) {
        Licence licence = getLicenceById(id);
        if(licence != null){
            licence.setSoftware(null);
        }
        licenceRepository.deleteById(id);
    }
}

