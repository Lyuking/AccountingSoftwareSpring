package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pin120.luka.AccountingSoftware.Models.LicenceType;
import ru.pin120.luka.AccountingSoftware.Services.LicenceTypeService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APILicenceTypesController {
    private LicenceTypeService licenceTypeService;
    @Autowired
    public void setLicenceTypeService(LicenceTypeService licenceTypeService){this.licenceTypeService = licenceTypeService;}
    @GetMapping("/licenceTypes")
    public Iterable<LicenceType> main(Model model){ return licenceTypeService.getAllLicenceTypes(); }
    /**
     * Добавление типа лицензии
     * */
    @GetMapping("/licenceTypes/adding")
    public String add(Model model){
        model.addAttribute("licenceType", new LicenceType());
        return "licenceTypes/adding";
    }
    @PostMapping("/licenceTypes/adding")
    public String add(@Valid LicenceType licenceType, BindingResult result, Model model){
        if(licenceTypeService.findByNameIgnoreCase(licenceType.getName()).size() > 0){
            result.rejectValue("name", "error.licenceType", "Это наименование типа лицензии уже используется");
        }
        if(result.hasErrors()){
            return  "licenceTypes/adding";
        }
        licenceTypeService.createLicenceType(licenceType);
        return "redirect:/licenceTypes";
    }
    /**
     * Изменение типа лицензии
     * */
    @GetMapping("/licenceTypes/update/{id}")
    public String update(@PathVariable Long id, Model model){
        LicenceType licenceType = licenceTypeService.getLicenceTypeById(id);
        model.addAttribute("licenceType", licenceType);
        return "licenceTypes/editing";
    }
    @PostMapping("/licenceTypes/update")
    public String update(@Valid LicenceType licenceType, BindingResult result, Model model){
        List<LicenceType> licenceTypeList = licenceTypeService.findByNameIgnoreCase(licenceType.getName());
            if(!licenceTypeList.isEmpty()){
                if(!licenceTypeList.get(0).getId().equals(licenceType.getId())){
                    result.rejectValue("name", "error.licenceType", "Это наименование типа лицензии уже используется");
                }
            }
        if(result.hasErrors()){
            return  "licenceTypes/editing";
        }
        licenceTypeService.updateLicenceType(licenceType);
        return "redirect:/licenceTypes";
    }
    /**
     * Удаление аудитории
     * */
    @GetMapping("/licenceTypes/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        licenceTypeService.deleteLicenceType(id);
        return "redirect:/licenceTypes";
    }
}
