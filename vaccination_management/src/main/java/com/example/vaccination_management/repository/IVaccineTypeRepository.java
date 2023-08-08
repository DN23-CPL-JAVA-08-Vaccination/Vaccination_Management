package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {
    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int vaccineTypeID);
}
