package com.example.vaccination_management.entity;

import javax.persistence.*;

@Entity
@Table(name = "vaccination")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date", columnDefinition = "Date" )
    private String date;

    @Column(name = "start_time", columnDefinition = "Datetime" )
    private String startTime;

    @Column(name = "end_time", columnDefinition = "Datetime" )
    private String endTime;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @Column(name = "duration")
    private String duration;

    @Column(name = "times")
    private int times;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "vaccination_type_id")
    private VaccinationType vaccinationType;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    public Vaccination() {
    }

        public Vaccination(int id, String date, String startTime, String endTime, String description, boolean deleteFlag, String duration, int times, Location location, VaccinationType vaccinationType, Vaccine vaccine) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleteFlag = deleteFlag;
        this.duration = duration;
        this.times = times;
        this.description = description;
        this.location = location;
        this.vaccinationType = vaccinationType;
        this.vaccine = vaccine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }
}
