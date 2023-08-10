package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {

//    Vaccine findById(int id);

//    List<Vaccine> findVaccineByVaccineType(VaccineType VaccineType);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided vaccine type.
     */
    Page<Vaccine> findByVaccineType(VaccineType VaccineType, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines associated with the specified vaccine type.
     */
    long countByVaccineType(VaccineType vaccineType);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines with names containing the provided search query (case-insensitive).
     */
    Page<Vaccine> findByNameContainingIgnoreCase(String searchQuery, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines with names containing the provided search query (case-insensitive).
     */
    long countByNameContainingIgnoreCase(String searchQuery);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided vaccine type and containing the search query in the name (case-insensitive).
     */
    Page<Vaccine> findByVaccineTypeAndNameContainingIgnoreCase(VaccineType vaccineType, String searchQuery, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines associated with the specified vaccine type and containing the search query in the name (case-insensitive).
     */
    long countByVaccineTypeAndNameContainingIgnoreCase(VaccineType vaccineType, String searchQuery);

}
