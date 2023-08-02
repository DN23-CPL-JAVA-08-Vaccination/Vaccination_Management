package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;

import java.util.List;

public interface IVaccineService {
    public List<Vaccine> getVaccinesDeleteFlagFalse();

    public List<Vaccine> getVaccinesDeleteFlagTrue();

    public List<Vaccine> getVaccinesByVaccineType(VaccineType vaccineType);

    public void updateVaccine(Vaccine updatedVaccine);

    public void destroyVaccine(int vaccineID) throws VaccineNotFoundException;

    public void deleteVaccine(int vaccineID);

    public void saveVaccine(Vaccine vaccine);

    public Vaccine getVaccineByID(int vaccineID) throws VaccineNotFoundException;

    public void restoreVaccine(int vaccineID);
}
