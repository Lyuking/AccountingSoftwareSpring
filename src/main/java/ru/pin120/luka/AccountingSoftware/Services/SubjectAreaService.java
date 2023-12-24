package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.SoftwareTechnicalDetails;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.SubjectAreaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectAreaService {
    private final SubjectAreaRepository subjectAreaRepository;

    @Autowired
    public SubjectAreaService(SubjectAreaRepository subjectAreaRepository) {
        this.subjectAreaRepository = subjectAreaRepository;
    }

    // Метод для создания предметной области
    public SubjectArea createSubjectArea(SubjectArea subjectArea) {
        return subjectAreaRepository.save(subjectArea);
    }

    // Метод для получения предметной области по ID
    public SubjectArea getSubjectAreaById(Long id) {
        Optional<SubjectArea> optionalSubjectArea = subjectAreaRepository.findById(id);
        return optionalSubjectArea.orElse(null);
    }

    // Метод для обновления предметной области
    public SubjectArea updateSubjectArea(SubjectArea subjectArea) {
        return subjectAreaRepository.save(subjectArea);
    }

    // Метод для получения всех предметных областей
    public List<SubjectArea> getAllSubjectAreas() {
        return (List<SubjectArea>) subjectAreaRepository.findAll();
    }

    // Метод для удаления предметной области по ID
    public void deleteSubjectArea(Long id) {
        SubjectArea subjectArea = subjectAreaRepository.findById(id).orElse(null);

        if (subjectArea != null) {
            for (SoftwareTechnicalDetails softwareTechnicalDetails : subjectArea.getSoftwareTechnicalDetailses()) {
                softwareTechnicalDetails.setSubjectArea(null);
            }
        }
        subjectAreaRepository.deleteById(id);
    }
    public List<SubjectArea> findByNameIgnoreCase(String name) { return subjectAreaRepository.findByNameIgnoreCase(name); }
    public boolean isNameUnique(String name, Long id){
        if(name != null && !name.equals("")){
            for (SubjectArea subjectArea : getAllSubjectAreas()){
                if(subjectArea.getName().equals(name)) {
                    if (id != null) {
                        if (!id.equals(subjectArea.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }
}

