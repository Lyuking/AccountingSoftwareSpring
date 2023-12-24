package ru.pin120.luka.AccountingSoftware.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class LicenceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "licence_type_id")
    private Long id;

    @NotEmpty
    @Column(name = "name", unique = true)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "licenceType")
    private List<Licence> licences;



    // Геттеры и сеттеры для всех полей (id, name, licences)

    // Методы для управления коллекцией licences
}

