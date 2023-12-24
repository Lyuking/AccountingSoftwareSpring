package ru.pin120.luka.AccountingSoftware.Controllers.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Models.LicenceDetails;
import ru.pin120.luka.AccountingSoftware.Services.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class APIInstalledSoftwareController {
    private AudienceService audienceService;
    private ComputerService computerService;
    private SoftwareService softwareService;
    private LicenceDetailsService licenceDetailsService;
    private SoftwareTechnicalDetailsService softwareTechnicalDetailsService;
    @Autowired
    public void setComputerService(ComputerService computerService){
        this.computerService = computerService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService){
        this.softwareService = softwareService;
    }
    @Autowired
    public void setLicenceDetailsService(LicenceDetailsService licenceDetailsService){
        this.licenceDetailsService = licenceDetailsService;
    }
    @Autowired
    public void setAudienceService(AudienceService audienceService){
        this.audienceService = audienceService;
    }
    @Autowired
    public void setSoftwareTechnicalDetailsService(SoftwareTechnicalDetailsService softwareTechnicalDetailsService){
        this.softwareTechnicalDetailsService = softwareTechnicalDetailsService;
    }
    @GetMapping("/installedSoftwares")
    public Iterable<Computer> main(Model model){
        return computerService.getAllComputers().stream()
                .filter(computer -> computer.getSoftwares().size() > 0)
                .sorted(Comparator.comparing(computer -> computer.getAudience() == null ? "" : computer.getAudience().getName()))
                .collect(Collectors.toList());

//        int softwareCount = allComputers.stream()
//                .mapToInt(computer -> computer.getSoftwares().size())
//                .sum();
//
//        model.addAttribute("computers", allComputers);
//        model.addAttribute("softwareCount", softwareCount);
//        return "installedSoftwares/index";
    }

    /**
     * Добавление ПО на ПК
     * */
    @GetMapping("/installedSoftwares/adding")
    public String selectAudienceToFilter(Model model){
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("url", "adding/");
        return "installedSoftwares/selectAudience";
    }
    @GetMapping("/installedSoftwares/adding/{audienceId}")
    public String SelectComputerToInsert(@PathVariable String audienceId, Model model){
        List<Computer> computers = null;
        if(audienceId == null || "empty".equals(audienceId)){
            computers = computerService.getAllComputers().stream().filter(t -> t.getAudience() == null).toList();
        }
        else{
            computers = computerService.getAllComputers().stream().filter(t -> t.getAudience() != null && t.getAudience().getName() == audienceService.getAudienceById(Long.valueOf(audienceId)).getName()).toList();
        }
        modelAddAudience(audienceId, model);
        model.addAttribute("computers", computers);
        model.addAttribute("url", audienceId + "/");
        return "installedSoftwares/selectComputer";
    }
    @GetMapping("/installedSoftwares/adding/{audienceId}/{computerId}")
    public String selectSoftwareToFilter(@PathVariable String audienceId, @PathVariable Long computerId, Model model){
        modelAddAudience(audienceId, model);
        if(computerId != null){
            model.addAttribute("computer", computerService.getComputerById(computerId));
            model.addAttribute("softwaresUnique", softwareTechnicalDetailsService.getAllSoftwareTechnicalDetails());
            model.addAttribute("url",   computerId + "/");
            return "installedSoftwares/selectSoftware";
        }
        return "redirect:/installedSoftwares/adding/" + audienceId;
    }


    @GetMapping("/installedSoftwares/adding/{audienceId}/{computerId}/{softwareDetailsId}")
    public String selectLicenceToInsert(@PathVariable String audienceId, @PathVariable Long computerId, @PathVariable Long softwareDetailsId, Model model){
        modelAddAudience(audienceId, model);
        if(computerId != null && softwareDetailsId != null){
            model.addAttribute("computer", computerService.getComputerById(computerId));
            model.addAttribute("currentSoftware",softwareTechnicalDetailsService.getSoftwareTechnicalDetailsById(softwareDetailsId));
            model.addAttribute("softwares", softwareService.getAllSoftware().stream().filter(t -> t.getSoftwareTechnicalDetails().getId().equals(softwareDetailsId) && t.getLicence().getLicenceDetails().getCount() > 0).toList());
            model.addAttribute("url",  softwareDetailsId + "/");
            return "installedSoftwares/selectLicence";
        }
        return "redirect:/installedSoftwares/adding/" + audienceId + "/" + computerId;
    }
    @GetMapping("/installedSoftwares/adding/{audienceId}/{computerId}/{softwareDetailsId}/{softwareId}")
    public String insertSoftwareToComputer(@PathVariable String audienceId, @PathVariable Long computerId, @PathVariable Long softwareDetailsId, @PathVariable Long softwareId, Model model){
        if(computerId != null && softwareId != null){
            Computer computer = computerService.getComputerById(computerId);
            if(computer != null){
                LicenceDetails licenceDetails = softwareService.getSoftwareById(softwareId).getLicence().getLicenceDetails();
                licenceDetails.setCount(licenceDetails.getCount() - 1);
                licenceDetailsService.updateLicenceDetails(licenceDetails);

                computer.getSoftwares().add(licenceDetails.getLicence().getSoftware());
                computerService.updateComputer(computer);
                return "redirect:/installedSoftwares";
            }
        }
        return "redirect:/installedSoftwares/adding/" + audienceId + "/" + computerId + "/" +softwareDetailsId;
    }
    /**
     * Удаление копии ПО у компьютера
     * @param computerId айди компьютера
     * @param softwareId айди ПО
     * @param model модель данных
     * @return редирект на installedSoftwares
     */
    @GetMapping("/installedSoftwares/delete/{computerId}/{softwareId}")
    public String delete(@PathVariable Long computerId, @PathVariable Long softwareId, Model model){
        LicenceDetails licenceDetails = softwareService.getSoftwareById(softwareId).getLicence().getLicenceDetails();
        licenceDetails.setCount(licenceDetails.getCount() + 1);
        licenceDetailsService.updateLicenceDetails(licenceDetails);

        computerService.deleteSoftware(computerId, softwareId);
        return "redirect:/installedSoftwares";
    }
    private void modelAddAudience(String audienceId, Model model) {
        model.addAttribute("audience", "empty".equals(audienceId == null? "empty" : audienceId)? "empty" : audienceService.getAudienceById(Long.valueOf(audienceId)));
    }

}
