package com.example.vaccination_management.entity;


import javax.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "production_date", columnDefinition = "Date")
    private String productionDate;

    @Column(name = "expiration_date", columnDefinition = "Date")
    private String expirationDate;

    @Column(name = "created_date", columnDefinition = "Date")
    private String createdDate;

    @Column(name = "updated_date", columnDefinition = "Date")
    private String updatedDate;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    public Inventory() {
    }

    public Inventory(int id, int quantity, String supplier, String productionDate, String expirationDate, String createdDate, String updatedDate, Vaccine vaccine) {
        this.id = id;
        this.quantity = quantity;
        this.supplier = supplier;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.vaccine = vaccine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }
}


