package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pin120.luka.AccountingSoftware.Models.Computer;
import ru.pin120.luka.AccountingSoftware.Models.TestEntity;

@RequestMapping("/api")
@RestController
public class APITestEntityController {
    @PostMapping("/test/adding")
    public TestEntity add(@RequestBody @Valid TestEntity testEntity, BindingResult result, Model model){
       return null;
    }
}
