package com.example.vaccination_management.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * QuangVT
 * Vaccination history DTO
 */
public interface IVaccinationHistoryDTO {
    Integer getId();

    String getPatientName();

    String getPatientBirth();

    String getVaccinationDesc();

    String getVaccineName();

    String getRegisTime();

    String getLastTime();

    Integer getVaccinationTimes();

    String getEmployeeName();
    Integer getEmployeePhone();

    Integer getStatus();

    String getGuardianName();

    String getGuardianPhone();
    String getPreStatus();
    Double getDosage();
    String getDuration();
    Integer getAgePatient();
    Integer getCompleteSchedule();
    Integer getAllSchedule();

    /**
     * QuangVT
     * format date
     */
    default String getRegisTimeFormatted() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date date = inputFormat.parse(getRegisTime());
            return outputFormat.format(date);
        } catch (ParseException e) {
            // Xử lý lỗi nếu có
            return getRegisTime();
        }
    }

    default String getBirthFormat() {
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

    default String getLastTimeFormatted() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date date = inputFormat.parse(getLastTime());
            return outputFormat.format(date);
        } catch (ParseException e) {
            return getLastTime();
        }
    }

}
