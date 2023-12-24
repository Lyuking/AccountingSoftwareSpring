package ru.pin120.luka.AccountingSoftware.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LicenceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "licence_details_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "licence_key")
    private String licenceKey;

    @NotNull
    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @NotNull
    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;


    @Column(name = "price")
    private float price;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Column(name = "count")
    private int count;

    @JsonBackReference
    @OneToOne(mappedBy = "licenceDetails", cascade = CascadeType.ALL)
    private Licence licence;

    public String getDateStart(){
        if(this.dateStart != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            return sdf.format(this.dateStart);
        }
        return null;
    }
    public void setDateStart(String val){
        if(val != null && !val.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                this.dateStart = sdf.parse(val);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String getDateEnd(){
        if(this.dateEnd != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            return sdf.format(this.dateEnd); // Форматируем дату в строку
        }
        return null;
    }
    public void setDateEnd(String val) {
        if (val != null && !val.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                this.dateEnd = sdf.parse(val);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}