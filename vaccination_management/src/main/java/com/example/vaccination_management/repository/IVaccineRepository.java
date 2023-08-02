package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {
    List<Vaccine> getVaccineByVaccineType(VaccineType vaccineType);

    List<Vaccine> findByDeleteFlagFalse();

    List<Vaccine> findByDeleteFlagTrue();

    Long countById(int vaccineID);
}
