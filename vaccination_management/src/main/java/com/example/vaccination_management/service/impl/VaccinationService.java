package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import com.example.vaccination_management.service.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.repository.IVaccinationRepository;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.repository.IVaccineRepository;


@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private  IVaccinationRepository iVaccinationRepository;

    @Autowired
   private  ILocationRepository iLocationRepository;

    @Autowired
   private IVaccinationTypeRepository iVaccinationTypeRepository;

    @Autowired
   private  IVaccineRepository iVaccineRepository;

    @Autowired
    private IPatientRepository iPatientRepository;


    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;


    public VaccinationService(IVaccinationRepository iVaccinationRepository, ILocationRepository iLocationRepository, IVaccinationTypeRepository iVaccinationTypeRepository, IVaccineRepository iVaccineRepository) {
        this.iVaccinationRepository = iVaccinationRepository;
        this.iLocationRepository = iLocationRepository;
        this.iVaccinationTypeRepository = iVaccinationTypeRepository;
        this.iVaccineRepository = iVaccineRepository;
    }
    /**
     * VuongVV
     * create vaccination information of Vaccination, admin after login
     */
    @Override
    public void saveVaccinationService(Vaccination vaccination , Location location, VaccinationType vaccinationType, Vaccine vaccine) {
        Location savedLocation = iLocationRepository.save(location); // Lưu địa chỉ trước để nhận id của địa chỉ sau khi lưu vào cơ sở dữ liệu
        vaccination.setLocation(savedLocation); // Gán địa chỉ có id đã lưu vào đối tượng person
        vaccination.setDate(String.valueOf(LocalDate.now()));
        VaccinationType savedVaccinationType=iVaccinationTypeRepository.save(vaccinationType);
        vaccination.setVaccinationType(savedVaccinationType);
        Vaccine savedVaccine=iVaccineRepository.save(vaccine);
        vaccination.setVaccine(savedVaccine);
        iVaccinationRepository.save(vaccination);

    }
    /**
     * VuongVV
     * get all list  information of Vaccination, admin after login
     */
    @Override
    public List<Vaccination> finAll() {
        return iVaccinationRepository.findAll();
    }
     /**
     * VuongVV
     * delete  information of Vaccination, admin after login
     */
    @Override
    public boolean deleteNotificationVaccination(int id) {
        try {
            iVaccinationRepository.deleteById(id);
            return true; // Xóa thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Xóa thất bại
        }
    }
     /**
     * VuongVV
     * get information by id of Vaccination, admin after login
     */
    @Override
    public Vaccination finById(int id) {
        return iVaccinationRepository.findById(id);
    }

     /**
     * VuongVV
     * Sen email by address location, admin after login
     */
//    @Override
//    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination) {
//        List<Patient> patients = iPatientRepository.findAll();
//        String locationName = vaccination.getLocation().getName();
//        List<String> matchingPatientNames = new ArrayList<>();
//        for (Patient patient : patients) {
//            if (patient.getAddress().toLowerCase().contains(locationName.toLowerCase())) {
//                matchingPatientNames.add(patient.getAccount().getEmail());
//            }
//        }
//        return matchingPatientNames;
//    }
    /**
     * VuongVV
     * Pagination soft list, admin after login
     */
    @Override
    public List<Vaccination> getVaccinationByPageV(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinePage = iVaccinationRepository.findByDeleteFlagFalse(pageable);
            return  vaccinePage.getContent();
    }

    @Override
    public long getTotalVaccination() {
        return iVaccinationRepository.count();
    }


    @Override
    public Page<IVaccinationDTO> getAllVaccination(String strSearch, Pageable pageable) {
        return iVaccinationRepository.getVaccinations(strSearch, pageable);
    }

    /**
     * LoanHTP
     * Adds a new vaccination history record.
     */
    @Override
    public void addVaccinationHistory(VaccinationHistory vaccinationHistory) {
        iVaccinationHistoryRepository.save(vaccinationHistory);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine.
     */
    @Override
    public List<Vaccination> findVaccinationByVaccine(Vaccine vaccine) {
        return iVaccinationRepository.findVaccinationByVaccine(vaccine);
    }

    /**
     * LoanHTP
     * Retrieves a vaccination record based on the provided ID.
     */
    @Override
    public Vaccination findVaccinationById(int id) {
        return iVaccinationRepository.findById(id);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations to display.
     */
    @Override
    public List<Vaccination> showVaccination() {
        return iVaccinationRepository.findAll();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccinations associated with the provided vaccine.
     */
    @Override
    public long getTotalVaccinationsByVaccine(Vaccine vaccine) {
        return iVaccinationRepository.countByVaccine(vaccine);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations based on the provided page number, size, and vaccine for pagination.
     */
    @Override
    public List<Vaccination> getVaccinationsByPageAndVaccine(int page, int size, Vaccine vaccine) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinationPage = iVaccinationRepository.findVaccinationByVaccine(vaccine, pageable);
        return vaccinationPage.getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of all vaccinations.
     */
    @Override
    public long getTotalVaccinations() {
        return iVaccinationRepository.count();
    }

    /**
<<<<<<< HEAD
     * VuongVV
     * delete soft for Vaccination, admin after login
     */
    @Override
    public void softDeleteVaccination(int id) {
        Vaccination vaccination = iVaccinationRepository.findById(id);
        if (vaccination != null) {
            vaccination.setDeleteFlag(true);
            iVaccinationRepository.save(vaccination);
        }
    }

     /**
     * VuongVV
     * softlist deleted for Vaccination, admin after login
     */
    @Override
    public List<Vaccination> getDeletedVaccinations() {
        return iVaccinationRepository.findByDeleteFlagTrue();
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccinations based on the provided page number and size for pagination.
     */
    @Override
    public List<Vaccination> getVaccinationByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinationPage = iVaccinationRepository.findAll(pageable);
        return vaccinationPage.getContent();
    }

}
