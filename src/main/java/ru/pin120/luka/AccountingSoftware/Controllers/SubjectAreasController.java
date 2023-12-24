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
import ru.pin120.luka.AccountingSoftware.Models.SubjectArea;
import ru.pin120.luka.AccountingSoftware.Services.SubjectAreaService;

import java.util.List;

@Controller
public class SubjectAreasController {
    private SubjectAreaService subjectAreaService;
    @Autowired
    public void setSubjectAreaService(SubjectAreaService subjectAreaService){this.subjectAreaService = subjectAreaService;}
    @GetMapping("/subjectAreas")
    public String main(Model model){
        List<SubjectArea> allSubjectAreas = subjectAreaService.getAllSubjectAreas();
        model.addAttribute("subjectAreas", allSubjectAreas);
        return "subjectAreas/index";
    }
    /**
     * Добавление предметной области
     * */
    @GetMapping("/subjectAreas/adding")
    public String add(Model model){
        model.addAttribute("subjectArea", new SubjectArea());
        return "subjectAreas/adding";
    }
    @PostMapping("/subjectAreas/adding")
    public String add(@Valid SubjectArea subjectArea, BindingResult result, Model model){
      if(subjectAreaService.findByNameIgnoreCase(subjectArea.getName()).size() > 0){
          result.rejectValue("name", "error.subjectArea", "Это имя уже используется");
      }
        if(result.hasErrors())
            return "subjectAreas/adding";
        subjectAreaService.createSubjectArea(subjectArea);
        return "redirect:/subjectAreas";
    }
    /**
     * Изменение предметной области
     * */
    @GetMapping("/subjectAreas/update/{id}")
    public String update(@PathVariable Long id, Model model){
        SubjectArea subjectArea = subjectAreaService.getSubjectAreaById(id);
        model.addAttribute("subjectArea", subjectArea);
        return "subjectAreas/editing";
    }
    @PostMapping("/subjectAreas/update")
    public String update(@Valid SubjectArea subjectArea, BindingResult result, Model model){
        List<SubjectArea> subjectAreas = subjectAreaService.findByNameIgnoreCase(subjectArea.getName());
        if(!subjectAreas.isEmpty()){
            if(!subjectAreas.get(0).getId().equals(subjectArea.getId())){
                result.rejectValue("name", "error.subjectArea", "Это имя уже используется");
            }
        }
        if(result.hasErrors())
            return "subjectAreas/editing";
        subjectAreaService.updateSubjectArea(subjectArea);
        return "redirect:/subjectAreas";
    }
    /**
     * Удаление предметной области
     * */
    @GetMapping("/subjectAreas/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        subjectAreaService.deleteSubjectArea(id);
        return "redirect:/subjectAreas";
    }
}
