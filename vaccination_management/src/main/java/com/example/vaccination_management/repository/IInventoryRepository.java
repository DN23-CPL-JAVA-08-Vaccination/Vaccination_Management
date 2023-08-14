package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {
    /**
     * HuyLVN
     * query inventories information of the same vaccine from the database
     */
    List<Inventory> getInventoriesByVaccine(Vaccine vaccine);
    /**
     * QuangVT
     * query update quantity vaccine when complete vaccination.
     */
    @Modifying
    @Query(value = "UPDATE inventory i " +
            "SET i.quantity = i.quantity - 1 " +
            "WHERE i.vaccine_id = ? AND i.expiration_date > CURRENT_DATE AND i.quantity > 0 " +
            "ORDER BY i.expiration_date ASC " +
            "LIMIT 1",
            nativeQuery = true
    )
    void updateInventoryQuantity(Integer vaccine_id);


    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int inventoryID);
}
