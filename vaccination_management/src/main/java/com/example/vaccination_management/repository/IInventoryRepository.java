package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> getInventoriesByVaccine(Vaccine vaccine);

    Long countById(int inventoryID);
}
