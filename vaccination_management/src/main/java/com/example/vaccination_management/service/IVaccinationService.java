package com.example.vaccination_management.service;


import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IVaccinationService {

    /**
     * VuongVV
     * create vaccination information of Vaccination, admin after login
     */
    public void saveVaccinationService(Vaccination vaccination, Location location, VaccinationType vaccinationType, Vaccine vaccine);

    /**
     * VuongVV
     * get all list  information of Vaccination, admin after login
     */
    List<Vaccination> finAll();

    /**
     * VuongVV
     * delete  information of Vaccination, admin after login
     */
    public boolean deleteNotificationVaccination(int id);

    /**
     * VuongVV
     * get information by id of Vaccination, admin after login
     */
    public Vaccination finById(int id);

    /**
     * VuongVV
     * Send email by address location, admin after login
     */
//    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination);


    /**
     * VuongVV
     * Pagination, admin after login
     */
    public List<Vaccination> getVaccinationByPageV(int pageNumber, int pageSize);

    public long getTotalVaccination();

    public void softDeleteVaccination(int id);

    public List<Vaccination> getDeletedVaccinations();

    Page<IVaccinationDTO> getAllVaccination(String strSearch, Pageable pageable);

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
