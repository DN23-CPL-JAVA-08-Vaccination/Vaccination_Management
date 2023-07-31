package com.example.vaccination_management.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class PatientDTO {
    private int id;

    private String name;
    private String healthInsurance;

    private Boolean gender;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String formBirthday;
    private String guardianName;
    private String guardianPhone;
    private String address;
    private String phone;
    private Boolean enableFlag;
    private Integer account;

    private String password;
    private Boolean deleteFlag;

    public PatientDTO() {
    }

    public PatientDTO(int id, String name, String healthInsurance, Boolean gender, String email, LocalDate birthday, String formBirthday, String guardianName, String guardianPhone, String address, String phone, Boolean enableFlag, Integer account, String password, Boolean deleteFlag) {
        this.id = id;
        this.name = name;
        this.healthInsurance = healthInsurance;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.formBirthday = formBirthday;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.address = address;
        this.phone = phone;
        this.enableFlag = enableFlag;
        this.account = account;
        this.password = password;
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

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getFormBirthday() {
        return formBirthday;
    }

    public void setFormBirthday(String formBirthday) {
        this.formBirthday = formBirthday;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}

