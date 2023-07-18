package com.example.vaccination_management.entity;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "vaccination_history_id")
    private VaccinationHistory vaccinationHistory;

    public Payment() {
    }

    public Payment(int id, Double price, VaccinationHistory vaccinationHistory) {
        this.id = id;
        this.price = price;
        this.vaccinationHistory = vaccinationHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public VaccinationHistory getVaccinationHistory() {
        return vaccinationHistory;
    }

    public void setVaccinationHistory(VaccinationHistory vaccinationHistory) {
        this.vaccinationHistory = vaccinationHistory;
    }
}
