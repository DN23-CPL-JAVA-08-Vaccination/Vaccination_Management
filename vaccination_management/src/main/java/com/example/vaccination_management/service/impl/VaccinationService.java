package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import com.example.vaccination_management.service.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

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


    public VaccinationService(IVaccinationRepository iVaccinationRepository, ILocationRepository iLocationRepository, IVaccinationTypeRepository iVaccinationTypeRepository, IVaccineRepository iVaccineRepository) {
        this.iVaccinationRepository = iVaccinationRepository;
        this.iLocationRepository = iLocationRepository;
        this.iVaccinationTypeRepository = iVaccinationTypeRepository;
        this.iVaccineRepository = iVaccineRepository;
    }


//    @Override
//    public List<VaccinationType> finAll() {
//        return null;
//    }

    @Override
    public void saveVaccinationService(Vaccination vaccination , Location location, VaccinationType vaccinationType, Vaccine vaccine) {
        Location savedLocation = iLocationRepository.save(location); // Lưu địa chỉ trước để nhận id của địa chỉ sau khi lưu vào cơ sở dữ liệu
        vaccination.setLocation(savedLocation); // Gán địa chỉ có id đã lưu vào đối tượng person
       // iVaccinationRepository.save(vaccination);

        VaccinationType savedVaccinationType=iVaccinationTypeRepository.save(vaccinationType);
        vaccination.setVaccinationType(savedVaccinationType);

        Vaccine savedVaccine=iVaccineRepository.save(vaccine);
        vaccination.setVaccine(savedVaccine);

        iVaccinationRepository.save(vaccination);

    }

    @Override
    public List<Vaccination> finAll() {
        return iVaccinationRepository.findAll();
    }


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

    @Override
    public Vaccination finById(int id) {
        return iVaccinationRepository.findById(id);
    }

    // sendEmail by ID
    @Override
    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination) {
        List<Patient> patients = iPatientRepository.findAll();
        String locationName = vaccination.getLocation().getName();
        List<String> matchingPatientNames = new ArrayList<>();

        for (Patient patient : patients) {
            if (patient.getAddress().toLowerCase().contains(locationName.toLowerCase())) {
                matchingPatientNames.add(patient.getAccount().getEmail());
            }
        }

        return matchingPatientNames;
    }
    //list-vaccine
    @Override
    public List<Vaccination> getVaccinationByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinePage = iVaccinationRepository.findAll(pageable);
        return vaccinePage.getContent();
    }

    @Override
    public long getTotalVaccination() {
        return iVaccineRepository.count();
    }
}
