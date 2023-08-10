package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVaccineService {

    /**
     * LoanHTP
     * Retrieves a list of vaccines to display.
     */
    List<Vaccine> showVaccines();

    /**
     * LoanHTP
     * Retrieves a vaccine record based on the provided ID.
     */
    Vaccine findVaccineById(int id);

//    List<Vaccine> findVaccineByVaccineType(VaccineType VaccineType);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number and size for pagination.
     */
    List<Vaccine> getVaccinesByPage(int page, int size);

    /**
     * LoanHTP
     * Gets the total count of all vaccines.
     */
    long getTotalVaccines();

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine and pagination details.
     */
    long getTotalVaccinesByVaccineType(VaccineType vaccineType);

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type.
     */
    List<Vaccine> getVaccinesByPageAndVaccineType(int page, int size, VaccineType vaccineType);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    List<Vaccine> getVaccinesByPageAndSearch(int page, int size, String searchQuery);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    long getTotalVaccinesBySearch(String searchQuery);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number, size, and search query for pagination.
     */
    List<Vaccine> getVaccinesByPageAndVaccineTypeAndSearch(int page, int size, VaccineType vaccineType, String searchQuery);

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided search query.
     */
    long getTotalVaccinesByVaccineTypeAndSearch(VaccineType vaccineType, String searchQuery);
}
