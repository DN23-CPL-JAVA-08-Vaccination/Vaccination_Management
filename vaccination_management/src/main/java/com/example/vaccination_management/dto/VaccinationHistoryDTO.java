package com.example.vaccination_management.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * QuangVT
 * Vaccine history DTO
 * use to update information
 */
public class VaccinationHistoryDTO {
    private Integer id;
    private String patientName;
    private String patientBirth;
    private String vaccinationDesc;
    private String vaccineName;
    private String regisTime;
    private String lastTime;
    private Integer vaccinationTimes;
    private String preStatus;
    private String employeeName;
    private Integer employeePhone;
    private Integer status;

    private String guardianName;
    private String guardianPhone;

    private Double dosage;
    private String duration;
    private Integer agePatient;


    public VaccinationHistoryDTO() {
    }

    public VaccinationHistoryDTO(Integer id, String patientName, String patientBirth, String vaccinationDesc, String vaccineName, String regisTime, String lastTime, Integer vaccinationTimes, String employeeName, Integer employeePhone, Integer status, String guardianName, String guardianPhone, String preStatus, Double dosage, String duration) {
        this.id = id;
        this.patientName = patientName;
        this.patientBirth = patientBirth;
        this.vaccinationDesc = vaccinationDesc;
        this.vaccineName = vaccineName;
        this.regisTime = regisTime;
        this.lastTime = lastTime;
        this.vaccinationTimes = vaccinationTimes;
        this.preStatus = preStatus;
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.status = status;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;

        this.dosage = dosage;
        this.duration = duration;

    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirth() {
        return patientBirth;
    }

    public Integer getAgePatient() {
        return agePatient;
    }

    public void setAgePatient(Integer agePatient) {
        this.agePatient = agePatient;
    }

    public void setPatientBirth(String patientBirth) {
        this.patientBirth = patientBirth;
    }

    public String getVaccinationDesc() {
        return vaccinationDesc;
    }

    public void setVaccinationDesc(String vaccinationDesc) {
        this.vaccinationDesc = vaccinationDesc;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getRegisTime() {
        return regisTime;
    }

    public void setRegisTime(String regisTime) {
        this.regisTime = regisTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getVaccinationTimes() {
        return vaccinationTimes;
    }

    public void setVaccinationTimes(Integer vaccinationTimes) {
        this.vaccinationTimes = vaccinationTimes;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(Integer employeePhone) {
        this.employeePhone = employeePhone;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(String preStatus) {
        this.preStatus = preStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getRegisTimeFormatted() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date date = inputFormat.parse(getRegisTime());
            return outputFormat.format(date);
        } catch (ParseException e) {
            // Xử lý lỗi nếu có
            return getRegisTime();
        }
    }

    public String getBirthFormat() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = inputFormat.parse(getPatientBirth());
            return outputFormat.format(date);
        } catch (ParseException e) {
            // Xử lý lỗi nếu có
            return getPatientBirth();
        }
    }

    public String getLastTimeFormatted() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date date = inputFormat.parse(getLastTime());
            return outputFormat.format(date);
        } catch (ParseException e) {
            return getLastTime();
        }
    }
}
