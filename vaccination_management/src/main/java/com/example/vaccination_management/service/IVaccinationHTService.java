package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;

import java.util.List;

public interface IVaccinationHTService {
    List<VaccinationHistory> finAll();
    public VaccinationHistory finById(int id);
    public VaccinationHistory saveVaccinationHistory(VaccinationHistory product);
    void updateStatusVaccinationHistory(Integer statusId, Integer vaccinationHR);
    public List<VaccinationHistory> getVaccinationHTByPage(int pageNumber, int pageSize);
    public long getTotalVaccinationHT();
}
