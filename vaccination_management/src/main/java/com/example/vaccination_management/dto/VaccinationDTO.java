package com.example.vaccination_management.dto;

import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.entity.VaccinationType;
import com.example.vaccination_management.entity.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccinationDTO {
    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private boolean enableFlag;
    private int duration;
    private int times;
    private Location location;
    private VaccinationType vaccinationType;
    private Vaccine vaccine;
}
