package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccinationType;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {

//    VaccineType findById(int id);
}
