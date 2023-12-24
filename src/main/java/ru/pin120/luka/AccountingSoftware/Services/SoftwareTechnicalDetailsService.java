package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.SoftwareTechnicalDetails;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.SoftwareTechnicalDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareTechnicalDetailsService {
    private final SoftwareTechnicalDetailsRepository softwareTechnicalDetailsRepository;

    @Autowired
    public SoftwareTechnicalDetailsService(SoftwareTechnicalDetailsRepository softwareTechnicalDetailsRepository) {
        this.softwareTechnicalDetailsRepository = softwareTechnicalDetailsRepository;
    }

    // Метод для создания технических деталей программного обеспечения
    public SoftwareTechnicalDetails createSoftwareTechnicalDetails(SoftwareTechnicalDetails technicalDetails) {
        return softwareTechnicalDetailsRepository.save(technicalDetails);
    }

    // Метод для получения технических деталей программного обеспечения по ID
    public SoftwareTechnicalDetails getSoftwareTechnicalDetailsById(Long id) {
        Optional<SoftwareTechnicalDetails> optionalTechnicalDetails = softwareTechnicalDetailsRepository.findById(id);
        return optionalTechnicalDetails.orElse(null);
    }

    // Метод для обновления технических деталей программного обеспечения
    public SoftwareTechnicalDetails updateSoftwareTechnicalDetails(SoftwareTechnicalDetails technicalDetails) {
        return softwareTechnicalDetailsRepository.save(technicalDetails);
    }

    // Метод для получения всех технических деталей программного обеспечения
    public List<SoftwareTechnicalDetails> getAllSoftwareTechnicalDetails() {
        return (List<SoftwareTechnicalDetails>) softwareTechnicalDetailsRepository.findAll();
    }

    // Метод для удаления технических деталей программного обеспечения по ID
    public List<SoftwareTechnicalDetails> findByNameIgnoreCase(String name) { return softwareTechnicalDetailsRepository.findByNameIgnoreCase(name); }
    public void deleteSoftwareTechnicalDetails(Long id) {
                softwareTechnicalDetailsRepository.deleteById(id);
    }
    public boolean isNameUnique(String name, Long id){
        if(name != null && !name.equals("")){
            for (SoftwareTechnicalDetails softwareTechnicalDetails : getAllSoftwareTechnicalDetails()){
                if(softwareTechnicalDetails.getName().equals(name)) {
                    if (id != null) {
                        if (!id.equals(softwareTechnicalDetails.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }
}

