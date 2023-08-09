package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationHistoryService implements IVaccinationHistoryService {
    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;


    /**
     * QuangVT
     * get all vaccination schedule
     */
@Override
public Page<IVaccinationHistoryDTO> getVaccinationSchedule(String strSearch, Pageable pageable) {
    return iVaccinationHistoryRepository.getVaccinationSchedule(strSearch,pageable);
}
    /**
     * QuangVT
     * count vaccination
     */
@Override
public IVaccinationHistoryDTO countVaccination(){
    return iVaccinationHistoryRepository.countVaccination();
};
    /**
     * QuangVT
     * get all vaccination history completed
     */
@Override
public Page<IVaccinationHistoryDTO> getHistoryVaccination(String strSearch, Pageable pageable) {
    return iVaccinationHistoryRepository.getHistoryVaccination(strSearch,pageable);
}
    /**
     * QuangVT
     * get details vaccination by id
     */
    @Override
    public IVaccinationHistoryDTO getVaccinationHistoryByID(Integer id) {
        return iVaccinationHistoryRepository.getVaccinationHistoryByID(id);
    }

    /**
     * QuangVT
     * get details vaccination by id
     */

    @Override
    public Page<IVaccinationHistoryDTO>  getVaccinationByPatient(Integer id, Pageable pageable) {
        return iVaccinationHistoryRepository.getVaccinationByPatient(id,pageable);
    }
    /**
     * QuangVT
     * get data for chart
     */

    @Override
    public List<Integer> getDataForChart(String year){
        return iVaccinationHistoryRepository.getDataForChart(year);
    }
    @Override
    public VaccinationHistory getById(Integer integer) {
        return iVaccinationHistoryRepository.getById(integer);
    }


    @Override
    public List<VaccinationHistory> findAll(Sort sort) {
        return iVaccinationHistoryRepository.findAll(sort);
    }

    @Override
    public <S extends VaccinationHistory> S save(S entity) {
        return iVaccinationHistoryRepository.save(entity);
    }
}
