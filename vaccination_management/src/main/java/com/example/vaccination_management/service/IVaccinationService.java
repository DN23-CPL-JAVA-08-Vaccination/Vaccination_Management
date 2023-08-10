package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.Vaccine;

import java.util.List;

public interface IVaccinationService {

//    public void addVaccination(Vaccination vaccination);

    /**
     * LoanHTP
     * Adds a new vaccination history record.
     */
    public void addVaccinationHistory(VaccinationHistory vaccinationHistory);

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine.
     */
    List<Vaccination> findVaccinationByVaccine(Vaccine vaccine);

    /**
     * LoanHTP
     * Retrieves a vaccination record based on the provided ID.
     */
    Vaccination findVaccinationById(int id);

    /**
     * LoanHTP
     * Retrieves a list of vaccinations to display.
     */
    List<Vaccination> showVaccination();

    /**
     * LoanHTP
     * Gets the total count of vaccinations associated with the provided vaccine.
     */
    long getTotalVaccinationsByVaccine(Vaccine vaccine);

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine and pagination details.
     */
    List<Vaccination> getVaccinationsByPageAndVaccine(int page, int size, Vaccine vaccine);

    /**
     * LoanHTP
     * Gets the total count of all vaccinations.
     */
    long getTotalVaccinations();

    /**
     * LoanHTP
     * Retrieves a list of vaccinations based on the provided pagination details.
     */
    List<Vaccination> getVaccinationByPage(int page, int size);

}
