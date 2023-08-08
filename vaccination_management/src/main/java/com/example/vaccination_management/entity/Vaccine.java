package com.example.vaccination_management.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createDate;

    @Column(name = "update_date", columnDefinition = "Datetime" )
    private LocalDateTime updateDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "vaccine_type_id")
    private VaccineType vaccineType;

    public Vaccine() {
    }

    public Vaccine(int id, String name, String code, String description, boolean enableFlag, LocalDateTime createDate, LocalDateTime updateDate, double price, int duration, VaccineType vaccineType) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.enableFlag = enableFlag;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.price = price;
        this.duration = duration;
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

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public LocalDateTime isCreateDate() {
        return createDate;
    }


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public VaccineType getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
    }
}
