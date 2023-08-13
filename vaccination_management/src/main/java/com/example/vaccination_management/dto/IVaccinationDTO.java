package com.example.vaccination_management.dto;

public interface IVaccinationDTO {
    Integer getId();
    String getDescription();
    String getDate();
    String getEndTime();
    String getStartTime();
    Integer getTimes();
    String getLocationName();
    String getVaccineName();
    String getVaccineTypeName();

}
