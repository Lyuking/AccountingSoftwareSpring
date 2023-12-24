package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Models.Licence;
import ru.pin120.luka.AccountingSoftware.Models.LicenceType;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.LicenceTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LicenceTypeService {
    private final LicenceTypeRepository licenceTypeRepository;

    @Autowired
    public LicenceTypeService(LicenceTypeRepository licenceTypeRepository) {
        this.licenceTypeRepository = licenceTypeRepository;
    }

    // Метод для создания типа лицензии
    public LicenceType createLicenceType(LicenceType licenceType) {
        return licenceTypeRepository.save(licenceType);
    }

    // Метод для получения типа лицензии по ID
    public LicenceType getLicenceTypeById(Long id) {
        Optional<LicenceType> optionalLicenceType = licenceTypeRepository.findById(id);
        return optionalLicenceType.orElse(null);
    }

    // Метод для обновления типа лицензии
    public LicenceType updateLicenceType(LicenceType licenceType) {
        return licenceTypeRepository.save(licenceType);
    }

    // Метод для получения всех типов лицензий
    public List<LicenceType> getAllLicenceTypes() {
        return (List<LicenceType>) licenceTypeRepository.findAll();
    }

    // Метод для удаления типа лицензии по ID
    public void deleteLicenceType(Long id) {
        LicenceType licenceType = licenceTypeRepository.findById(id).orElse(null);

        if (licenceType != null) {
            // Устанавливаем связи с компьютерами в null
            for (Licence licence : licenceType.getLicences()) {
                licence.setLicenceType(null);
            }
            licenceTypeRepository.deleteById(id);
        }
    }
    public List<LicenceType> findByNameIgnoreCase(String name) { return licenceTypeRepository.findByNameIgnoreCase(name); }
    public boolean isNameUnique(String name, Long id){
        if(name != null && !name.equals("")){
            for (LicenceType licenceType : getAllLicenceTypes()){
                if(licenceType.getName().equals(name)) {
                    if (id != null) {
                        if (!id.equals(licenceType.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }
}

