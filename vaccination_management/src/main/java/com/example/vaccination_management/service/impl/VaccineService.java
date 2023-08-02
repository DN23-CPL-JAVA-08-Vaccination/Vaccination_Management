package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineService implements IVaccineService {
    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Override
    public List<Vaccine> getVaccinesDeleteFlagFalse() {
        return iVaccineRepository.findByDeleteFlagFalse();
    }

    @Override
    public List<Vaccine> getVaccinesDeleteFlagTrue() {
        return iVaccineRepository.findByDeleteFlagTrue();
    }

    @Override
    public List<Vaccine> getVaccinesByVaccineType(VaccineType vaccineType) {
        return iVaccineRepository.getVaccineByVaccineType(vaccineType);
    }

    @Override
    public void saveVaccine(Vaccine vaccine) {
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        vaccine.setCreateDate(createDate.format(formatter));
        vaccine.setUpdateDate(updateDate.format(formatter));
        vaccine.setDeleteFlag(false);

        iVaccineRepository.save(vaccine);
    }

    @Override
    public Vaccine getVaccineByID(int vaccineID) throws VaccineNotFoundException {
        Optional<Vaccine> vaccine = iVaccineRepository.findById(vaccineID);

        if (vaccine.isPresent()) {
            return vaccine.get();
        }

        throw new VaccineNotFoundException("Couldn't find any vaccines with ID");
    }

    @Override
    public void updateVaccine(Vaccine updatedVaccine) {
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        updatedVaccine.setUpdateDate(updateDate.format(formatter));

        iVaccineRepository.save(updatedVaccine);
    }

    @Override
    public void destroyVaccine(int vaccineID) throws VaccineNotFoundException {
        Long count = iVaccineRepository.countById(vaccineID);

        if (count == null || count == 0) {
            throw new VaccineNotFoundException("Couldn't find any vaccines with ID");
        }
        iVaccineRepository.deleteById(vaccineID);
    }

    @Override
    public void deleteVaccine(int vaccineID) {
        Optional<Vaccine> vaccineOptional = iVaccineRepository.findById(vaccineID);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();

            vaccine.setDeleteFlag(true);

            iVaccineRepository.save(vaccine);
        }
    }

    @Override
    public void restoreVaccine(int vaccineID) {
        Optional<Vaccine> vaccineOptional = iVaccineRepository.findById(vaccineID);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();

            vaccine.setDeleteFlag(false);

            iVaccineRepository.save(vaccine);
        }
    }
}
