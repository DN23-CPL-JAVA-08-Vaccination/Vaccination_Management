package com.example.vaccination_management.entity;

import javax.persistence.*;
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "health_insurance")
    private String healthInsurance;

    @Column(name = "gender")
    private int gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "birthday", columnDefinition = "Date")
    private String birthday;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    @Column(name = "enable_flag")
    private boolean enableFlag;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;



    public Patient() {
    }

    public Patient(int id, String name, String healthInsurance, int gender, String address, String phoneNumber, String birthday, String guardianName, String guardianPhone, boolean enableFlag, Account account) {
        this.id = id;
        this.name = name;
        this.healthInsurance = healthInsurance;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.enableFlag = enableFlag;
        this.account = account;
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

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}