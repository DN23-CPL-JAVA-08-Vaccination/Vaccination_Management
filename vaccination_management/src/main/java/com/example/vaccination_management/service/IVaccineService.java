package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;

import java.util.List;

public interface IVaccineService {
    List<Vaccine> findAll();
}
