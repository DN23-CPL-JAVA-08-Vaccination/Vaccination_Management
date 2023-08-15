package com.example.vaccination_management.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    @Column(name = "create_date", columnDefinition = "Datetime")
    private String createDate;

    @Column(name = "update_date", columnDefinition = "Datetime")
    private String updateDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private String duration;

    @Column(name = "age")
    private String age;

    @Column(name = "image")
    private String image;

    @Column(name = "dosage")
    private Double dosage;

    @ManyToOne
    @JoinColumn(name = "vaccine_type_id")
    private VaccineType vaccineType;

    public Vaccine() {
    }

    public Vaccine(int id, String name, String code, String description, boolean deleteFlag, String createDate, String updateDate, Double price, String duration, String age, String image, Double dosage, VaccineType vaccineType) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.deleteFlag = deleteFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.price = price;
        this.duration = duration;
        this.age = age;
        this.image = image;
        this.dosage = dosage;
        this.vaccineType = vaccineType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public VaccineType getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
    }
}

