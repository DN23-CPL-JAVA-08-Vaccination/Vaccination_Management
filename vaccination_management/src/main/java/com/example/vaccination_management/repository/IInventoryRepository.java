package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {
    /**
     * HuyLVN
     * query inventories information of the same vaccine from the database
     */
    List<Inventory> getInventoriesByVaccine(Vaccine vaccine);

    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int inventoryID);
}
