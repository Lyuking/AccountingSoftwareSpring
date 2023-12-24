package ru.pin120.luka.AccountingSoftware.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "licence_id")
    private Long id;

    @ManyToOne
   /* @JsonManagedReference*/
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
   /* @JsonManagedReference*/
    @JoinColumn(name = "licence_type_id")
    private LicenceType licenceType;

    @OneToOne
   /* @JsonManagedReference*/
    @JoinColumn(name = "licence_details_id")
    private LicenceDetails licenceDetails;

    @JsonBackReference
    @OneToOne(mappedBy = "licence", orphanRemoval = true)
    private Software software;




    // Геттеры и сеттеры для всех полей (id, employee, licenceType, licenceDetails, softwares)

    // Методы для управления коллекцией softwares
}

