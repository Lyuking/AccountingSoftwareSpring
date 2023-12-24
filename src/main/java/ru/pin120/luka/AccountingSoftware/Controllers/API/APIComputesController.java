package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Services.AudienceService;
import ru.pin120.luka.AccountingSoftware.Services.ComputerService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIComputesController {
    private ComputerService computerService;
    private AudienceService audienceService;
    @Autowired
    public void setComputerService(ComputerService computerService){
        this.computerService = computerService;
    }
    @Autowired
    public void setAudienceService(AudienceService audienceService){
        this.audienceService = audienceService;
    }
    @GetMapping("/computers")
    public Iterable<Computer> main(Model model){
        return computerService.getAllComputers();
    }
    /**
     * Добавление компьютера
     * */

    @PostMapping("/computers/adding")
    public Computer add(@RequestBody @Valid Computer computer, BindingResult result, Model model){
        if(!computerService.findByNumberIgnoreCase(computer.getNumber()).isEmpty()){
            throw new EntityExistsException("Компьютер с данным номером уже существует");
        }
        if(result.hasErrors()){
            throw new ValidationException("Компьютер не прошел валидацию");
        }
        return computerService.createComputer(computer);
    }
    /**
     * Изменение компьютера
     * */

    @PutMapping("/computers/update")
    public Computer update(@RequestBody @Valid Computer computer, BindingResult result, Model model){
        List<Computer> computerList = computerService.findByNumberIgnoreCase(computer.getNumber());
        if(!computerList.isEmpty()){
            if(!computerList.get(0).getId().equals(computer.getId())){
                throw new EntityExistsException("Данный номер компьютера уже используется");
            }
        }
        if(result.hasErrors()){
            throw new ValidationException("Компьютер не прошел валидацию");
        }
        return computerService.updateComputer(computer);
    }
    /**
     * Удаление компьютера
     * */
    @GetMapping("/computers/delete/{id}")
    public HttpStatusCode delete(@PathVariable("id") Long id){
        Computer computer = computerService.getComputerById(id);
        if(computer == null) {
            throw new EntityNotFoundException("Компьютера с указанным идентификатором не существует");
        }
        computerService.deleteComputer(id);
        return HttpStatusCode.valueOf(200);
    }
    /**
     * Детализация компьютера
     * */
    @GetMapping("/computers/details/{id}")
    public Computer details(@PathVariable Long id, Model model){
        Computer computer = computerService.getComputerById(id);
        if(computer == null){
            throw new EntityNotFoundException("Компьютера с указанным идентификатором не существует");
        }
        return computer;
    }

    private Computer setAudienceIfExist(Computer computer, String audienceId){
        if(audienceId != null && !"".equals(audienceId) && !"empty".equals(audienceId))
            computer.setAudience(audienceService.getAudienceById(Long.parseLong(audienceId)));
        else
            computer.setAudience(null);
        return computer;
    }
}
