package com.example.vaccination_management.dto;

import com.example.vaccination_management.entity.Account;

import javax.persistence.*;

public class PatientLDTO {

    private int id;

    private String name;

    private String healthInsurance;

    private boolean gender;

    private String address;

    private String phoneNumber;

    private String birthday;

    private String guardianName;

    private String guardianPhone;

    private boolean deleteFlag;

    private Account account;

    public PatientLDTO() {
    }

    public PatientLDTO(int id, String name, String healthInsurance, boolean gender, String address, String phoneNumber, String birthday, String guardianName, String guardianPhone, boolean deleteFlag, Account account) {
        this.id = id;
        this.name = name;
        this.healthInsurance = healthInsurance;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.deleteFlag = deleteFlag;
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
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

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
