package com.example.vaccination_management.dto;

public interface IPatientDTO {

    Integer getId();
    String getName();
    String getHealthInsurance();
    Boolean getGender();

    String getAddress();
    String getPhone();
    String getBirthday();


    String getGuardianName();

    String getGuardianPhone();
}
