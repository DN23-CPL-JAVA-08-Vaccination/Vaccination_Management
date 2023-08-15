package com.example.vaccination_management.entity;

import javax.persistence.*;

@Entity
@Table(name = "vaccine_type")
public class VaccineType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public VaccineType() {
    }

    public VaccineType(int id, String name, boolean deleteFlag) {
        this.id = id;
        this.name = name;
        this.deleteFlag = deleteFlag;
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

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
