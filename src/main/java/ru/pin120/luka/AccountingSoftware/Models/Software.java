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
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "software_id")
    private Long id;

    @ManyToOne
    /*@JsonManagedReference*/
    @JoinColumn(name = "software_technical_details_id")
    private SoftwareTechnicalDetails softwareTechnicalDetails;

    @OneToOne
   /* @JsonManagedReference*/
    @JoinColumn(name = "licence_id")
    private Licence licence;

    @JsonBackReference
    @ManyToMany(mappedBy = "softwares")
    private List<Computer> computers;


}
