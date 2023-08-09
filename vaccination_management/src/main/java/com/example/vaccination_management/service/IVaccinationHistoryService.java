package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IVaccinationHistoryService {


    Page<IVaccinationHistoryDTO> getVaccinationSchedule(String strSearch, Pageable pageable);

    IVaccinationHistoryDTO countVaccination();





    Page<IVaccinationHistoryDTO> getHistoryVaccination(String strSearch, Pageable pageable);

    IVaccinationHistoryDTO getVaccinationHistoryByID(Integer id);

    Page<IVaccinationHistoryDTO>  getVaccinationByPatient(Integer id, Pageable pageable);

    List<Integer> getDataForChart(String year);

    VaccinationHistory getById(Integer integer);



    List<VaccinationHistory> findAll(Sort sort);


    <S extends VaccinationHistory> S save(S entity);
}
