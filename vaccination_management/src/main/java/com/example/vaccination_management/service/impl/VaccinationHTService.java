package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.service.IVaccinationHTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class VaccinationHTService implements IVaccinationHTService {

    private IVaccinationHistoryRepository iVaccinationHistoryRepository;

    @Autowired
    public VaccinationHTService(IVaccinationHistoryRepository iVaccinationHistoryRepository) {
        this.iVaccinationHistoryRepository = iVaccinationHistoryRepository;
    }
    @Override
    public List<VaccinationHistory> finAll() {
        return iVaccinationHistoryRepository.findAll();
    }

    @Override
    public VaccinationHistory finById(int id) {
        return iVaccinationHistoryRepository.findById(id);
    }

    @Override
    public VaccinationHistory saveVaccinationHistory(VaccinationHistory product) {
        return iVaccinationHistoryRepository.save(product);
    }


    @Override
    public void updateStatusVaccinationHistory(Integer statusId, Integer vaccinationHR) {
        iVaccinationHistoryRepository.updateStatusVaccinationHistory(statusId, vaccinationHR);
    }

    /**
     * ThangLV
     * get information of employee, user after login
     */

    @Override
    public List<VaccinationHistory> getVaccinationHTByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VaccinationHistory> vaccinationHTPage = iVaccinationHistoryRepository.findAll(pageable);
        return vaccinationHTPage.getContent();
    }
    @Override
    public long getTotalVaccinationHT() {
        return iVaccinationHistoryRepository.count();
    }

}



