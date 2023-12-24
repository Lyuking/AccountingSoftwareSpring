package ru.pin120.luka.AccountingSoftware.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class SoftwareTechnicalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "software_technical_details_id")
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    /*@JsonManagedReference*/
    @JoinColumn(name = "subject_area_id")
    private SubjectArea subjectArea;
    @Size(min = 0, max = 3000)
    @Column(name = "description")
    private String description;

    @Column(name = "required_space")
    private String requiredSpace;

    @Column(name = "photo")
    private String photo;

    @JsonBackReference
    @OneToMany(mappedBy = "softwareTechnicalDetails", cascade = CascadeType.ALL)
    private List<Software> softwares;


}

