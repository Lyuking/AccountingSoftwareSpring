package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pin120.luka.AccountingSoftware.Models.SoftwareTechnicalDetails;
import ru.pin120.luka.AccountingSoftware.Services.SoftwareTechnicalDetailsService;
import ru.pin120.luka.AccountingSoftware.Services.SubjectAreaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static ru.pin120.luka.AccountingSoftware.AccountingSoftwareApplication.UPLOAD_DIRECTORY;

@RequestMapping("/api")
@RestController
public class APISoftwaresController {

    private SoftwareTechnicalDetailsService softwareTechnicalDetailsService;
    private SubjectAreaService subjectAreaService;
    @Autowired
    public void setSoftwareTechnicalDetailsService(SoftwareTechnicalDetailsService softwareTechnicalDetailsService){
        this.softwareTechnicalDetailsService = softwareTechnicalDetailsService;
    }
    @Autowired
    public void setSubjectAreaService(SubjectAreaService subjectAreaService){
        this.subjectAreaService = subjectAreaService;
    }
    @GetMapping("/softwares")
    public Iterable<SoftwareTechnicalDetails> main(Model model){ return softwareTechnicalDetailsService.getAllSoftwareTechnicalDetails(); }
    /**
     * Добавление ПО
     * */
    @GetMapping("/softwares/selectSubjectAreaI")
    public String selectSubjectAreaToInsert(Model model){
        model.addAttribute("subjectAreas", subjectAreaService.getAllSubjectAreas());
        model.addAttribute("url", "adding/");
        return "softwares/selectSubjectArea";
    }
    @GetMapping("/softwares/adding/{id}")
    public String add(@PathVariable String id, Model model){
        SoftwareTechnicalDetails softwareTechnicalDetails = new SoftwareTechnicalDetails();
        softwareTechnicalDetails = setSubjectAreaIfExist(softwareTechnicalDetails, id);

        model.addAttribute("softwareTechnicalDetails", softwareTechnicalDetails);
        return "softwares/adding";
    }
    @PostMapping("/softwares/adding")
    public String add(@Valid SoftwareTechnicalDetails softwareTechnicalDetails, BindingResult result, Model model, @RequestParam("image") MultipartFile file) throws IOException {
        if(softwareTechnicalDetailsService.findByNameIgnoreCase(softwareTechnicalDetails.getName()).size() > 0){
            result.rejectValue("name", "error.softwareTechnicalDetails", "Это имя уже используется");
        }
        if(result.hasErrors()){
            model.addAttribute("softwareTechnicalDetails", softwareTechnicalDetails);
            return "softwares/adding";
        }
        if(file != null && !file.isEmpty()){
            String fullFileName = saveFile(file);
            softwareTechnicalDetails.setPhoto(fullFileName);
        }
        softwareTechnicalDetailsService.createSoftwareTechnicalDetails(softwareTechnicalDetails);
        return "redirect:/softwares";
    }

    /**
     * Изменение ПО
     * */
    @GetMapping("/softwares/selectSubjectAreaU/{id}")
    public String selectSubjectAreaToUpdate(@PathVariable Long id, Model model){
        model.addAttribute("subjectAreas", subjectAreaService.getAllSubjectAreas());
        model.addAttribute("url", "editing/" + id + "/");
        return "softwares/selectSubjectArea";
    }
    @GetMapping("/softwares/selectSubjectAreaU/editing/{softwareTechnicalDetailsId}/{id}")
    public String update(@PathVariable Long softwareTechnicalDetailsId, @PathVariable String id, Model model){
        SoftwareTechnicalDetails softwareTechnicalDetails = softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(softwareTechnicalDetailsId);
        softwareTechnicalDetails = setSubjectAreaIfExist(softwareTechnicalDetails, id);

        model.addAttribute("softwareTechnicalDetails", softwareTechnicalDetails);
        return "softwares/editing";
    }
    @PostMapping("/softwares/update")
    public String update(@Valid SoftwareTechnicalDetails softwareTechnicalDetails, BindingResult result, Model model, @RequestParam("image") MultipartFile file) throws IOException{
        List<SoftwareTechnicalDetails> softwareTechnicalDetailsList = softwareTechnicalDetailsService.findByNameIgnoreCase(softwareTechnicalDetails.getName());
        if(!softwareTechnicalDetailsList.isEmpty()){
            if(!softwareTechnicalDetailsList.get(0).getId().equals(softwareTechnicalDetails.getId())){
                result.rejectValue("name", "error.softwareTechnicalDetails", "Это имя уже используется");
            }
        }
        if(result.hasErrors()){
            model.addAttribute("softwareTechnicalDetails", softwareTechnicalDetails);
            return "softwares/editing";
        }
        if(file != null && !file.isEmpty()){
            String fullFileName = saveFile(file);
            if(softwareTechnicalDetails.getPhoto() != null && !"".equals(softwareTechnicalDetails.getPhoto())){
                Path oldFileNameAndPath = Paths.get(UPLOAD_DIRECTORY, softwareTechnicalDetails.getPhoto());
                Files.delete(oldFileNameAndPath);
            }
            softwareTechnicalDetails.setPhoto(fullFileName);
        }
        softwareTechnicalDetailsService.updateSoftwareTechnicalDetails(softwareTechnicalDetails);
        SoftwareTechnicalDetails tmp1 = softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(softwareTechnicalDetails.getId());
        softwareTechnicalDetailsService.updateSoftwareTechnicalDetails(softwareTechnicalDetails);
        SoftwareTechnicalDetails tmp2 = softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(softwareTechnicalDetails.getId());
        return "redirect:/softwares";
    }
    /**
     * Удаление ПО
     * */
    @GetMapping("/softwares/delete/{id}")
    public String delete(@PathVariable("id") Long id) throws IOException {
        SoftwareTechnicalDetails softwareTechnicalDetails = softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(id);
        if(softwareTechnicalDetails != null && softwareTechnicalDetails.getPhoto() != null){
            Path oldFileNameAndPath = Paths.get(UPLOAD_DIRECTORY, softwareTechnicalDetails.getPhoto());
            Files.delete(oldFileNameAndPath);
        }

        softwareTechnicalDetailsService.deleteSoftwareTechnicalDetails(id);
        return "redirect:/softwares";
    }
    /**
     * Детализация ПО
     * */
    @GetMapping("/softwares/details/{id}")
    public String details(@PathVariable Long id, Model model){
        SoftwareTechnicalDetails softwareTechnicalDetails = softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(id);
        if(softwareTechnicalDetails != null && softwareTechnicalDetails.getPhoto() != null){
            model.addAttribute("imagePath", Paths.get(UPLOAD_DIRECTORY, softwareTechnicalDetails.getPhoto()).toString());
        }
        model.addAttribute("software", softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(id));
        return "softwares/details";
    }

    private SoftwareTechnicalDetails setSubjectAreaIfExist(SoftwareTechnicalDetails softwareTechnicalDetails, String subjectAreaId){
        if(subjectAreaId != null && !"".equals(subjectAreaId) && !"empty".equals(subjectAreaId))
            softwareTechnicalDetails.setSubjectArea(subjectAreaService.getSubjectAreaById(Long.parseLong(subjectAreaId)));
        else
            softwareTechnicalDetails.setSubjectArea(null);
        return softwareTechnicalDetails;
    }

    /**
     *
     * @param file
     * @return Путь к сохраненному файлу
     * @throws IOException
     */
    private static String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String uniqueID = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fullFileName = uniqueID + fileExtension;

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fullFileName);
        Files.write(fileNameAndPath, file.getBytes());
        return fullFileName;
    }
}
