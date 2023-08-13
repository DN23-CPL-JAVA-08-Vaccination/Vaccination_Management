package com.example.vaccination_management.dto;

import java.time.LocalDate;

public interface IAccountDetailDTO {
    Integer getId();
    String getPatientHealthInsurance();
    String getPassword();
    String getEmail();
    String getPatientName();
    LocalDate getBirthday();
    Boolean getGender();
    String getAddress();
    String getPhone();
    String getPatientGuardianName();
    String getPatientGuardianPhone();
    Boolean getAccountEnableFlag();
    Boolean getPatientDeleteFlag();

}
