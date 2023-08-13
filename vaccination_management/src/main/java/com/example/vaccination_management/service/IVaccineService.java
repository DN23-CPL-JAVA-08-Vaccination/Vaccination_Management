package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;

import java.util.List;

public interface IVaccineService {
     /**
     * VuongLV
     * get all information of Vaccine, admin after login
     */
    List<Vaccine> findAll();
}
