package ru.pin120.luka.AccountingSoftware.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Services.AudienceService;
import ru.pin120.luka.AccountingSoftware.Services.ComputerService;

import java.util.List;

@Controller
public class ComputesController {
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
    public String main(Model model){
        List<Computer> allComputers = computerService.getAllComputers();
        model.addAttribute("computers", allComputers);
        return "computers/index";
    }
    /**
     * Добавление компьютера
     * */
    @GetMapping("/computers/selectAudienceI")
    public String selectAudienceToInsert(Model model){
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("url", "adding/");
        return "computers/selectAudience";
    }
    @GetMapping("/computers/adding/{id}")
    public String add(@PathVariable String id, Model model){
        Computer computer = new Computer();
        computer = setAudienceIfExist(computer, id);

        model.addAttribute("computer", computer);
        return "computers/adding";
    }
    @PostMapping("/computers/adding")
    public String add(@Valid Computer computer, BindingResult result, Model model){
        if(!computerService.findByNumberIgnoreCase(computer.getNumber()).isEmpty()){
            result.rejectValue("number", "error.computer", "Этот номер компьютера уже используется");
        }
        if(result.hasErrors()){
            return "computers/adding";
        }
        computerService.createComputer(computer);
        return "redirect:/computers";
    }
    /**
     * Изменение компьютера
     * */
    @GetMapping("/computers/selectAudienceU/{id}")
    public String selectAudienceToUpdate(@PathVariable Long id, Model model){
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("url", "editing/" + id + "/");
        return "computers/selectAudience";
    }
    @GetMapping("/computers/selectAudienceU/editing/{computerId}/{id}")
    public String update(@PathVariable Long computerId, @PathVariable String id, Model model){
        Computer computer = computerService.getComputerById(computerId);
        computer = setAudienceIfExist(computer, id);

        model.addAttribute("computer", computer);
        return "computers/editing";
    }
    @PostMapping("/computers/update")
    public String update(@Valid Computer computer, BindingResult result, Model model){
        List<Computer> computerList = computerService.findByNumberIgnoreCase(computer.getNumber());
        if(!computerList.isEmpty()){
            if(!computerList.get(0).getId().equals(computer.getId())){
                result.rejectValue("number", "error.computer", "Этот номер компьютера уже используется");
            }
        }
        if(result.hasErrors()){
            return "computers/editing";
        }
        computerService.updateComputer(computer);
        return "redirect:/computers";
    }
    /**
     * Удаление компьютера
     * */
    @GetMapping("/computers/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        computerService.deleteComputer(id);
        return "redirect:/computers";
    }
    /**
     * Детализация компьютера
     * */
    @GetMapping("/computers/details/{id}")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("computer", computerService.getComputerById(id));
        return "computers/details";
    }

    private Computer setAudienceIfExist(Computer computer, String audienceId){
        if(audienceId != null && !"".equals(audienceId) && !"empty".equals(audienceId))
            computer.setAudience(audienceService.getAudienceById(Long.parseLong(audienceId)));
        else
            computer.setAudience(null);
        return computer;
    }
}
