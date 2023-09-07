package com.example.vaccination_management.dto;

import com.example.vaccination_management.entity.Employee;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.VaccinationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

public class VaccinationHistoryDTO {

    private Vaccination vaccination;

    private Patient patient;

    private VaccinationStatus vaccinationStatus;

    private String preStatus;

    private double dosage;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    private String guardianName;

    private String guardianPhone;

    private boolean deleteflag;

    private int vaccinationTimes;

    private int patientId;

    private int vaccinationId;

    private int employeeId;

    private int vaccinationStatusId;

    public VaccinationHistoryDTO() {
    }

    public VaccinationHistoryDTO( Vaccination vaccination, Patient patient, VaccinationStatus vaccinationStatus, String preStatus, double dosage, String startTime, String endTime, String guardianName, String guardianPhone, boolean deleteflag, int vaccinationTimes, int patientId, int vaccinationId, int employeeId, int vaccinationStatusId) {
        this.vaccinationStatus = vaccinationStatus;
        this.patient = patient;
        this.vaccination = vaccination;
        this.preStatus = preStatus;
        this.dosage = dosage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.deleteflag = deleteflag;
        this.vaccinationTimes = vaccinationTimes;
        this.patientId = patientId;
        this.vaccinationId = vaccinationId;
        this.employeeId = employeeId;
        this.vaccinationStatusId = vaccinationStatusId;
    }

    public VaccinationStatus getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(VaccinationStatus vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    public String getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(String preStatus) {
        this.preStatus = preStatus;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public boolean isDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(boolean deleteflag) {
        this.deleteflag = deleteflag;
    }

    public int getVaccinationTimes() {
        return vaccinationTimes;
    }

    public void setVaccinationTimes(int vaccinationTimes) {
        this.vaccinationTimes = vaccinationTimes;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getVaccinationStatusId() {
        return vaccinationStatusId;
    }

    public void setVaccinationStatusId(int vaccinationStatusId) {
        this.vaccinationStatusId = vaccinationStatusId;
    }
}