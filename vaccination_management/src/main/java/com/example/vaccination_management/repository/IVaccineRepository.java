package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {
    /**
     * HuyLVN
     * query vaccines information of the same type of vaccine from the database
     */
    List<Vaccine> getVaccineByVaccineType(VaccineType vaccineType);

    /**
     * HuyLVN
     * query the vaccine information that has the deleteFlag field as false from the database
     */
    List<Vaccine> findByDeleteFlagFalse();

    /**
     * HuyLVN
     * query the vaccine information that has the deleteFlag field as true from the database
     */
    List<Vaccine> findByDeleteFlagTrue();

    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int vaccineID);
}
