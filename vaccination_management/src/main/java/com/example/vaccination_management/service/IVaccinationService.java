package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.service.impl.VaccineService;

import java.util.List;

public interface IVaccinationService {

   // List<VaccinationType> finAll();
    public void saveVaccinationService (Vaccination vaccination, Location location, VaccinationType vaccinationType, Vaccine vaccine);

    List<Vaccination> finAll();

    public boolean deleteNotificationVaccination(int id);
    public Vaccination finById(int id);
    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination);
    public List<Vaccination> getVaccinationByPage(int page, int size);
    public long getTotalVaccination();

}



