package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Models.Software;
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Repositories.AudienceRepository;
import ru.pin120.luka.AccountingSoftware.Repositories.ComputerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComputerService {
    private final ComputerRepository computerRepository;

    @Autowired
    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    // Метод для создания аудитории
    public Computer createComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    // Метод для получения аудитории по ID
    public Computer getComputerById(Long id) {
        return computerRepository.findById(id).orElse(null);
    }

    // Метод для обновления аудитории
    public Computer updateComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    public List<Computer> getAllComputers() {
        return (List<Computer>) computerRepository.findAll();
    }

    // Метод для удаления аудитории по ID
    public void deleteComputer(Long id) {
        Computer computer = computerRepository.findById(id).orElse(null);
        if (computer != null) {
            for (Software software : computer.getSoftwares()) {
                software.setComputers(null);
            }
            computerRepository.deleteById(id);
        }
    }
    public void deleteSoftware(Long computerId, Long softwareId){
        Computer computer = computerRepository.findById(computerId).orElse(null);
        if(computer != null){
            computer.setSoftwares(computer.getSoftwares().stream()
                    .filter(software -> !software.getId().equals(softwareId))
                    .collect(Collectors.toList()));
            updateComputer(computer);
        }
    }
    public List<Computer> findByNumberIgnoreCase(String number) { return computerRepository.findByNumberIgnoreCase(number); }
    public boolean isNumberUnique(String number, Long id){
        if(number != null && !number.equals("")){
            for (Computer computer : getAllComputers()){
                if(computer.getNumber().equals(number)) {
                    if (id != null) {
                        if (!id.equals(computer.getId()))
                            return false;
                    }
                    else return false;
                }
            }
        }
        return true;
    }

}
