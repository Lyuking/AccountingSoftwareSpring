package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.AudienceRepository;

import java.util.List;
@Service
public class AudienceService {
    private final AudienceRepository audienceRepository;

    @Autowired
    public AudienceService(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    // Метод для создания аудитории
    public Audience createAudience(Audience audience) {
        return audienceRepository.save(audience);
    }

    // Метод для получения аудитории по ID
    public Audience getAudienceById(Long id) {
        return audienceRepository.findById(id).orElse(null);
    }

    // Метод для обновления аудитории
    public Audience updateAudience(Audience audience) {
        return audienceRepository.save(audience);
    }

    public List<Audience> getAllAudiences() {
        return (List<Audience>) audienceRepository.findAll();
    }

    // Метод для удаления аудитории по ID
    public void deleteAudience(Long id) {

        Audience audience = audienceRepository.findById(id).orElse(null);

        if (audience != null) {
            // Устанавливаем связи с компьютерами в null
            for (Computer computer : audience.getComputers()) {
                computer.setAudience(null);
            }

            // Удаляем аудиторию
            audienceRepository.deleteById(id);
        }
    }
    public List<Audience> findByNameIgnoreCase(String name){ return audienceRepository.findByNameIgnoreCase(name); }
    public boolean isNameUnique(String name, Long id){
        if(name != null && !name.equals("")){
            for (Audience audience : getAllAudiences()){
                if(audience.getName().equals(name)) {
                    if (id != null) {
                        if (!id.equals(audience.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }
}
