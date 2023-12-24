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
import ru.pin120.luka.AccountingSoftware.Models.*;
import ru.pin120.luka.AccountingSoftware.Services.*;

import java.util.List;
import java.util.Optional;

@Controller
public class LicencesController {
    private SoftwareService softwareService;
    private LicenceService licenceService;
    private LicenceDetailsService licenceDetailsService;
    private SoftwareTechnicalDetailsService softwareTechnicalDetailsService;

    private LicenceTypeService licenceTypeService;
    private EmployeeService employeeService;
    private ComputerService computerService;
    @Autowired
    public void setSoftwareService(SoftwareService softwareService){
        this.softwareService = softwareService;
    }
    @Autowired
    public void setLicenceService(LicenceService licenceService){
        this.licenceService = licenceService;
    }
    @Autowired
    public void setLicenceDetailsService(LicenceDetailsService licenceDetailsService){ this.licenceDetailsService = licenceDetailsService; }
    @Autowired
    public void setSoftwareTechnicalDetailsService(SoftwareTechnicalDetailsService softwareTechnicalDetailsService){ this.softwareTechnicalDetailsService = softwareTechnicalDetailsService;}
    @Autowired
    public void setLicenceTypeService(LicenceTypeService licenceTypeService){ this.licenceTypeService = licenceTypeService;}
    @Autowired
    public void setEmployeeService(EmployeeService employeeService){ this.employeeService = employeeService;}
    @Autowired
    public void setComputerService(ComputerService computerService){ this.computerService = computerService;}
    @GetMapping("/licences")
    public String main(Model model){
        List<Software> allSoftwares = softwareService.getAllSoftware();
        model.addAttribute("softwareLicences", allSoftwares);
        return "licences/index";
    }
    /**
     * Добавление копии ПО
     * */
    @GetMapping("/licences/adding")
    public String selectSoftwareToInsert(Model model){
        model.addAttribute("softwares", softwareTechnicalDetailsService.getAllSoftwareTechnicalDetails());
        model.addAttribute("url", "adding/");
        return "licences/selectSoftware";
    }
    @GetMapping("/licences/adding/{softwareId}")
    public String SelectLicenceTypeToInsert(@PathVariable Long softwareId, Model model){
        model.addAttribute("licenceTypes", licenceTypeService.getAllLicenceTypes());
        model.addAttribute("url", softwareId + "/");
        return "licences/selectLicenceType";
    }
    @GetMapping("/licences/adding/{softwareId}/{licenceTypeId}")
    public String selectEmployeeToInsert(@PathVariable Long softwareId, @PathVariable String licenceTypeId, Model model){
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("url",   licenceTypeId + "/");
        return "licences/selectEmployee";
    }
    @GetMapping("/licences/adding/{softwareId}/{licenceTypeId}/{employeeId}")
    public String addingLicence(@PathVariable Long softwareId, @PathVariable String licenceTypeId, @PathVariable String employeeId, Model model){
        addAttributesToModel(licenceTypeId, employeeId, model);
        return "licences/adding";
    }
    @PostMapping("/licences/adding/{softwareId}/{licenceTypeId}/{employeeId}")
    public String addingLicence(@PathVariable Long softwareId, @PathVariable String licenceTypeId, @PathVariable String employeeId, @Valid @ModelAttribute("licenceDetails") LicenceDetails licenceDetails, BindingResult result, Model model){
        if(licenceDetailsService.findByKeyIgnoreCase(licenceDetails.getLicenceKey()).size() > 0){
            result.rejectValue("licenceKey", "error.licenceDetails", "Этот номер ключа уже используется");
        }
        if(result.hasErrors()){
            addAttributesToModel(licenceTypeId, employeeId, model);
            return "licences/adding";
        }
        //СПОРНЫЙ МОМЕНТ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        licenceDetails.setLicenceKey(licenceDetails.getLicenceKey() == null ? "" : licenceDetails.getLicenceKey());
        if(softwareId != null) {
            softwareService.createSoftware(new Software(null, softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(softwareId),
                    licenceService.createLicence(new Licence(null, "empty".equals(employeeId == null ? "empty" : employeeId) ? null : employeeService.getEmployeeById(Long.valueOf(employeeId)),
                            "empty".equals(licenceTypeId == null ? "empty" : licenceTypeId) ? null : licenceTypeService.getLicenceTypeById(Long.valueOf(licenceTypeId)), licenceDetailsService.createLicenceDetails(licenceDetails), null)),
                    null));
        }
        return "redirect:/licences";
    }

    /**
     * Удаление копии ПО
     * */
    @GetMapping("/licences/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Optional<Software> software = softwareService.getAllSoftware().stream().filter(t -> t.getLicence().getLicenceDetails().getId().equals(id)).findFirst();
        if(software.isPresent()){
            //Предварительное удаление связанных копий у компьютеров, вынужденная мера из-за каскадного удаления licenceDetails -> Licences -> Softwares
            computerService.getAllComputers().forEach(t -> computerService.deleteSoftware(t.getId(), software.get().getId()));
            licenceDetailsService.deleteLicenceDetails(id);
        }
        return "redirect:/licences";
    }
    /**
     * Изменение данных
     */
    @GetMapping("/licences/updateType/{licenceId}")
    public String changeLicenceType(@PathVariable Long licenceId, Model model){
        model.addAttribute("licenceTypes", licenceTypeService.getAllLicenceTypes());
        model.addAttribute("url", licenceId + "/");
        return "licences/selectLicenceType";
    }
    @GetMapping("/licences/updateType/{licenceId}/{typeId}")
    public String changeLicenceType(@PathVariable Long licenceId, @PathVariable String typeId, Model model){
        Licence licence = licenceService.getLicenceById(licenceId);
        licence.setLicenceType("empty".equals(typeId == null? "empty" : typeId)? null : licenceTypeService.getLicenceTypeById(Long.valueOf(typeId)));
        licenceService.updateLicence(licence);
        return "redirect:/licences";
    }
    @GetMapping("/licences/updateEmployee/{licenceId}")
    public String changeEmployee(@PathVariable Long licenceId, Model model){
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("url", licenceId + "/");
        return "licences/selectEmployee";
    }
    @GetMapping("/licences/updateEmployee/{licenceId}/{employeeId}")
    public String changeEmployee(@PathVariable Long licenceId, @PathVariable String employeeId, Model model){
        Licence licence = licenceService.getLicenceById(licenceId);
        licence.setEmployee("empty".equals(employeeId == null? "empty" : employeeId)? null : employeeService.getEmployeeById(Long.valueOf(employeeId)));
        licenceService.updateLicence(licence);
        return "redirect:/licences";
    }
    @GetMapping("/licences/updateDetails/{licenceId}")
    public String changeLicenceDetails(@PathVariable Long licenceId, Model model){
        Licence licence = licenceService.getLicenceById(licenceId);
        addAttributesToModel(model, licence);
        return "licences/adding";
    }
    @PostMapping("/licences/updateDetails/{licenceId}")
    public String changeLicenceDetails(@Valid @ModelAttribute("licenceDetails") LicenceDetails licenceDetails, BindingResult result, Model model){
        List<LicenceDetails> licenceDetailsList = licenceDetailsService.findByKeyIgnoreCase(licenceDetails.getLicenceKey());
        if(!licenceDetailsList.isEmpty()){
            if(!licenceDetailsList.get(0).getId().equals(licenceDetails.getId())){
                result.rejectValue("licenceKey", "error.licenceDetails", "Этот номер ключа уже используется");
            }
        }
        if(result.hasErrors()){
            Licence licence = licenceDetailsService.getLicenceDetailsById(licenceDetails.getId()).getLicence();
            addAttributesToModel(model, licence);
            return "licences/adding";
        }
        //СПОРНЫЙ МОМЕНТ11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
        licenceDetails.setLicenceKey(licenceDetails.getLicenceKey() == null ? "" : licenceDetails.getLicenceKey());
        licenceDetailsService.updateLicenceDetails(licenceDetails);
        return "redirect:/licences";
    }

    private static void addAttributesToModel(Model model, Licence licence) {
        model.addAttribute("licenceDetails", licence.getLicenceDetails());
        model.addAttribute("licenceType", licence.getLicenceType() == null ? "empty" : licence.getLicenceType());
        model.addAttribute("employee", licence.getEmployee() == null ? "empty" : licence.getEmployee());
        model.addAttribute("url", licence.getId());
    }
    private void addAttributesToModel(String licenceTypeId, String employeeId, Model model) {
        model.addAttribute("licenceDetails", new LicenceDetails());
        model.addAttribute("url", employeeId);
        model.addAttribute("employee", "empty".equals(employeeId == null? "empty" : employeeId) ? "empty" : employeeService.getEmployeeById(Long.valueOf(employeeId)));
        model.addAttribute("licenceType", "empty".equals(licenceTypeId == null? "empty" : licenceTypeId) ? "empty" : licenceTypeService.getLicenceTypeById(Long.valueOf(licenceTypeId)));
    }
}
