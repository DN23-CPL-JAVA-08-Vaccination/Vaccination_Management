package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccinationStatus;

public interface IVaccineStatusService {
    VaccinationStatus getById(Integer integer);
}
