package ru.pin120.luka.AccountingSoftware.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Services.AudienceService;

import java.util.List;
@Controller
public class AudiencesController {
    private AudienceService audienceService;
    @Autowired
    public void setAudienceService(AudienceService audienceService){
        this.audienceService = audienceService;
    }
    @GetMapping("/audiences")
    public String main(Model model){
        List<Audience> allAudiences = audienceService.getAllAudiences();
        model.addAttribute("audiences", allAudiences);
        return "audiences/index";
    }
    /**
     * Добавление аудитории
     * */
    @GetMapping("/audiences/adding")
    public String add(Model model){
        model.addAttribute("audience", new Audience());
        return "audiences/adding";
    }
    @PostMapping("/audiences/adding")
    public String add(@Valid Audience audience, BindingResult result, Model model){
        if(!audienceService.findByNameIgnoreCase(audience.getName()).isEmpty()){
            result.rejectValue("name", "error.audience", "Это имя уже используется");
        }
        if(result.hasErrors()){
            return "audiences/adding";
        }
        audienceService.createAudience(audience);
        return "redirect:/audiences";
    }
    /**
     * Изменение аудитории
     * */
    @GetMapping("/audiences/update/{id}")
    public String update(@PathVariable Long id, Model model){
        Audience audience = audienceService.getAudienceById(id);
        model.addAttribute("audience", audience);
        return "audiences/editing";
    }
    @PostMapping("/audiences/update")
    public String update(@Valid Audience audience, BindingResult result, Model model){
        List<Audience> audienceList = audienceService.findByNameIgnoreCase(audience.getName());
        if(!audienceList.isEmpty()){
            if(!audienceList.get(0).getId().equals(audience.getId())){
                result.rejectValue("name", "error.audience", "Это имя уже используется");
            }
        }
        if(result.hasErrors()){
            return "audiences/editing";
        }
        audienceService.updateAudience(audience);
        return "redirect:/audiences";
    }
    /**
     * Удаление аудитории
     * */
    @GetMapping("/audiences/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        audienceService.deleteAudience(id);
        return "redirect:/audiences";
    }

}
//    @GetMapping("/audiences/details/{id}")
//    public String details(@RequestParam(name="name", required = false, defaultValue = "По умолчанию") String name, Model model){
//        model.addAttribute("author", name);
//        return "audiences/details";
//    }