package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.entity.VaccinationType;

import java.util.List;

public interface IVaccinationTypeService {
    List<VaccinationType> finAll();
}
