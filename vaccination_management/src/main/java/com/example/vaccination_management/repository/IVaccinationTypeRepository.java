package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.entity.VaccinationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccinationTypeRepository extends JpaRepository<VaccinationType, Integer> {

//    VaccinationType findVaccinationTypeById(int id);
}
