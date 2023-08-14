package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {
    /**
     * HuyLVN
     * query inventories information of the same vaccine and delete flag is false from the database
     */
    List<Inventory> getInventoriesByVaccineAndDeleteFlagFalse(Vaccine vaccine);

    /**
     * HuyLVN
     * query inventories information of the same vaccine and delete flag is true from the database
     */
    List<Inventory> getInventoriesByVaccineAndDeleteFlagTrue(Vaccine vaccine);


    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int inventoryID);

    /**
     * HuyLVN
     * get all information of inventories has delete flag is false
     */
    List<Inventory> findInventoriesByDeleteFlagFalse();

    /**
     * HuyLVN
     * get all information of inventories has delete flag is true.
     */
    List<Inventory> findInventoriesByDeleteFlagTrue();
}
