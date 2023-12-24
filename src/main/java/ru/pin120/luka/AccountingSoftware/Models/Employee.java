package ru.pin120.luka.AccountingSoftware.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 255)
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 255)
    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private List<Licence> licences;

    @Transient
    public String getFullName() {
        return surname + " " + name + " " + (patronymic != null ? patronymic : "");
    }

}

