package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Models.LicenceDetails;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.LicenceDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LicenceDetailsService {
    private final LicenceDetailsRepository licenceDetailsRepository;

    @Autowired
    public LicenceDetailsService(LicenceDetailsRepository licenceDetailsRepository) {
        this.licenceDetailsRepository = licenceDetailsRepository;
    }

    // Метод для создания деталей лицензии
    public LicenceDetails createLicenceDetails(LicenceDetails licenceDetails) {
        return licenceDetailsRepository.save(licenceDetails);
    }

    // Метод для получения деталей лицензии по ID
    public LicenceDetails getLicenceDetailsById(Long id) {
        Optional<LicenceDetails> optionalLicenceDetails = licenceDetailsRepository.findById(id);
        return optionalLicenceDetails.orElse(null);
    }

    // Метод для обновления деталей лицензии
    public LicenceDetails updateLicenceDetails(LicenceDetails licenceDetails) {
        return licenceDetailsRepository.save(licenceDetails);
    }

    // Метод для получения всех деталей лицензий
    public List<LicenceDetails> getAllLicenceDetails() {
        return (List<LicenceDetails>) licenceDetailsRepository.findAll();
    }

    // Метод для удаления деталей лицензии по ID
    public void deleteLicenceDetails(Long id) {
        licenceDetailsRepository.deleteById(id);
    }

    public List<LicenceDetails> findByKeyIgnoreCase(String key) { return licenceDetailsRepository.findByLicenceKeyIgnoreCase(key); }

    public boolean isKeyUnique(String key, Long id){
        if(key != null && !key.equals("")){
            for (LicenceDetails licenceDetails : getAllLicenceDetails()){
                if(licenceDetails.getLicenceKey().equals(key)) {
                    if (id != null) {
                        if (!id.equals(licenceDetails.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }
}

