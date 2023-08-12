package com.example.vaccination_management.dto;

import java.util.Date;

public interface InforPatientDTO {

    int getId();
    String getName();
    String getAddress();
    Date getBirthday();
    boolean getGender();
    String getHealthInsurance();
    String getPhone();
    String getGuardianName();
    String getGuardianPhone();
    String getEmail();
    String getUsername();

}
