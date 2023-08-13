package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.service.impl.VaccineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IVaccinationService {

   /**
   * VuongVV
   * create vaccination information of Vaccination, admin after login
   */
    public void saveVaccinationService (Vaccination vaccination, Location location, VaccinationType vaccinationType, Vaccine vaccine);
    /**
    * VuongVV
    * get all list  information of Vaccination, admin after login
    */
    List<Vaccination> finAll();
    /**
     * VuongVV
     * delete  information of Vaccination, admin after login
    */
    public boolean deleteNotificationVaccination(int id);
    /**
    * VuongVV
    * get information by id of Vaccination, admin after login
    */
    public Vaccination finById(int id);
    /**
    * VuongVV
    * Send email by address location, admin after login
    */
    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination);
    /**
    * VuongVV
    * Pagination, admin after login
    */
    public List<Vaccination> getVaccinationByPage(int pageNumber, int pageSize);
 //public List<Vaccination> getDeletedVaccinations(int page, int size);
    public long getTotalVaccination();
 public void softDeleteVaccination(int id);


// public List<Vaccination> getDeletedVaccinations();

 public List<Vaccination> getDeletedVaccinations();


}



