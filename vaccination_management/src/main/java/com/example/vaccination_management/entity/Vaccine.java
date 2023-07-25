package com.example.vaccination_management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "vaccine")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "enable_flag")
    private boolean enableFlag;

    @Column(name = "create_date", columnDefinition = "Datetime" )
    private String createDate;

    @Column(name = "update_date", columnDefinition = "Datetime" )
    private String updateDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "vaccine_type_id")
    private VaccineType vaccineType;

    public Vaccine() {
    }


}
