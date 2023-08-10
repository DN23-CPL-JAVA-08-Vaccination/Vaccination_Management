package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {


    /**
     *LoanHTP
     *get information about vaccination by vaccine
     */
    List<Vaccination> findVaccinationByVaccine(Vaccine vaccine);

//    Vaccination findVaccinationById(int id);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination records based on the provided vaccine.
     */
    Page<Vaccination> findVaccinationByVaccine(Vaccine vaccine, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccination records associated with the specified vaccine.
     */
    long countByVaccine(Vaccine vaccine);
}
