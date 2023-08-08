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

    @Column(name = "enable_flag")
    private boolean enableFlag;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "times")
    private int times;

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

    public Vaccination(int id, String date, String startTime, String endTime, boolean enableFlag, int duration, int times, Location location, VaccinationType vaccinationType, Vaccine vaccine) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enableFlag = enableFlag;
        this.duration = duration;
        this.times = times;
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

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
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
