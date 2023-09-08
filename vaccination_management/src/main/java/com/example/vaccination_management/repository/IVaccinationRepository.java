package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {

    /**
     *LoanHTP
     *get information by id
     */
    Vaccination findById(int id);

    /**
     * LoanHTP
     * Retrieves a pageable list of vaccinations that have not been marked for deletion.
     */
    Page<Vaccination> findByDeleteFlagFalse(Pageable pageable);

    /**
     *LoanHTP
     *get information about vaccination by vaccine
     */
    @Query("SELECT v FROM Vaccination v WHERE v.vaccine = :vaccine AND v.deleteFlag = false")
    List<Vaccination> findVaccinationByVaccineAndDeleteFlagFalse(@Param("vaccine") Vaccine vaccine);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination records based on the provided vaccine.
     */
    Page<Vaccination> findVaccinationByVaccineAndDeleteFlagFalse(Vaccine vaccine, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccination records associated with the specified vaccine.
     */
    @Query("SELECT COUNT(v) FROM Vaccination v WHERE v.vaccine = :vaccine AND v.deleteFlag = false")
    long countByVaccineAndDeleteFlagFalse(@Param("vaccine") Vaccine vaccine);

    /**
     * LoanHTP
     * Counts the number of vaccines that have not been marked for deletion.
     */
    long countByDeleteFlagFalse();

}
