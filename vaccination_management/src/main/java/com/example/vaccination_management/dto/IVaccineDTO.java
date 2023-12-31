package com.example.vaccination_management.dto;

import com.example.vaccination_management.utils.FormatPrice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * QuangVT
 * Vaccine history DTO
 */
public interface IVaccineDTO {
    Integer getId();
    String getCode();

    String getVaccineName();

    String getDescription();

    String getDuration();

    Double getPrice();

    String getCreateDate();

    String getTypeName();
    default String  getPriceFormat(){
        FormatPrice formatPrice = new FormatPrice();
        String formattedPrice = formatPrice.formatCurrency(getPrice());
        return formattedPrice;
    }




    default String getDateFormat() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date date = inputFormat.parse(getCreateDate());
            return outputFormat.format(date);
        } catch (ParseException e) {
            return getCreateDate();
        }
    }
}
