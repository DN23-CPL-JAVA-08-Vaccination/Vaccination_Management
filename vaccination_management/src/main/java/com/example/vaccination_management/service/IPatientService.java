package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IPatientService {

    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId);
}
