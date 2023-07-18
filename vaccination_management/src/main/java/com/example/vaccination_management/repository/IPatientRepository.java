package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepository extends JpaRepository<Patient, Integer> {

}
