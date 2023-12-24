package ru.pin120.luka.AccountingSoftware.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_id")
    private Long id;


    @Column(name = "number", unique = true)
    @NotEmpty
    @Size(min = 1, max = 100)
    private String number;

    @ManyToOne
    /*@JsonManagedReference*/
    @JoinColumn(name = "audience_id")
    private Audience audience;

    @Column(name = "ip_address")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "{COMPUTER.IPV4.ERROR}")
    private String ipAddress;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String processor;

    @Size(min = 1, max = 255)
    private String videocard;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String ram;

    @NotEmpty
    @Size(min = 1, max = 255)
    @Column(name = "total_space")
    private String totalSpace;

    @ManyToMany
    /*@JsonManagedReference*/
    /*@JsonIgnore*/
    @JoinColumn(name = "software_id")
    private List<Software> softwares;
}

