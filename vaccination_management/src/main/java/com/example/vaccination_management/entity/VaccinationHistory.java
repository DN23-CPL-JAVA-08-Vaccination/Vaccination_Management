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
    private double dosage;

    @Column(name = "start_time", columnDefinition = "Datetime" )
    private String startTime;

    @Column(name = "end_time", columnDefinition = "Datetime" )
    private String endTime;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    @Column(name = "delete_flag")
    private boolean deleteflag;

    @Column(name = "vaccination_times")
    private int vaccinationTimes;

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

    public VaccinationHistory(int id, String preStatus, double dosage, String startTime, String endTime, String guardianName, String guardianPhone, boolean deleteflag, int vaccinationTimes, Patient patient, Vaccination vaccination, Employee employee, VaccinationStatus vaccinationStatus) {
        this.id = id;
        this.preStatus = preStatus;
        this.dosage = dosage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.deleteflag = deleteflag;
        this.vaccinationTimes = vaccinationTimes;
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
