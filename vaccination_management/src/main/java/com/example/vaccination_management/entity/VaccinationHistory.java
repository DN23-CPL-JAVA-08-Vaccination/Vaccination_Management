package com.example.vaccination_management.entity;

import javax.persistence.*;

@Entity
@Table(name = "vaccination_history")
public class VaccinationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "pre_status")
    private String preStatus;

    @Column(name = "dosage")
    private Double dosage;


    @Column(name = "start_time", columnDefinition = "Datetime")
    private String startTime;

    @Column(name = "end_time", columnDefinition = "Datetime")
    private String endTime;

    @Column(name = "delete_flag")
    private boolean deleteFlag;


    @Column(name = "vaccination_times")
    private int vaccinationTimes;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    @ManyToOne
    @JoinColumn(name = "vaccination_id")
    private Vaccination vaccination;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "vaccination_status_id")
    private VaccinationStatus vaccinationStatus;

    public VaccinationHistory() {
    }

    public VaccinationHistory(int id, String preStatus, Double dosage, String startTime, String endTime, boolean deleteFlag, int vaccinationTimes, String guardianName, String guardianPhone, Patient patient, Vaccination vaccination, Employee employee, VaccinationStatus vaccinationStatus) {
        this.id = id;
        this.preStatus = preStatus;
        this.dosage = dosage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleteFlag = deleteFlag;
        this.vaccinationTimes = vaccinationTimes;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.patient = patient;
        this.vaccination = vaccination;
        this.employee = employee;
        this.vaccinationStatus = vaccinationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(String preStatus) {
        this.preStatus = preStatus;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
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

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getVaccinationTimes() {
        return vaccinationTimes;
    }

    public void setVaccinationTimes(int vaccinationTimes) {
        this.vaccinationTimes = vaccinationTimes;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public VaccinationStatus getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(VaccinationStatus vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
}
