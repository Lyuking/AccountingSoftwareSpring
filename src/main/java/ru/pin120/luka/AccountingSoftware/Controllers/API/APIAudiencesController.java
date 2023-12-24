package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pin120.luka.AccountingSoftware.Models.Audience;
import ru.pin120.luka.AccountingSoftware.Services.AudienceService;

import java.util.List;
@RequestMapping("/api")
@RestController
public class APIAudiencesController {
    private AudienceService audienceService;
    @Autowired
    public void setAudienceService(AudienceService audienceService){
        this.audienceService = audienceService;
    }
    @GetMapping("/audiences")
    public Iterable<Audience> main(Model model){
        return audienceService.getAllAudiences();
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
    public Audience add(@RequestBody @Valid Audience audience, BindingResult result, Model model){
        if(!audienceService.findByNameIgnoreCase(audience.getName()).isEmpty()){
            throw new EntityExistsException("Компьютер с данным номером уже существует");
        }
        if(result.hasErrors()){
            throw new ValidationException("Аудитория не прошла валидацию");
        }
        return audienceService.createAudience(audience);
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
//    @GetMapping("/audiences/details/{id}")
//    public String details(@RequestParam(name="name", required = false, defaultValue = "По умолчанию") String name, Model model){
//        model.addAttribute("author", name);
//        return "audiences/details";
//    }
}
