package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {
    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int vaccineTypeID);

    /**
     * HuyLVN
     * query the vaccine type information that has the deleteFlag field as false from the database
     */
    List<VaccineType> findByDeleteFlagFalse();

    /**
     * HuyLVN
     * query the vaccine type information that has the deleteFlag field as true from the database
     */
    List<VaccineType> findByDeleteFlagTrue();
}
