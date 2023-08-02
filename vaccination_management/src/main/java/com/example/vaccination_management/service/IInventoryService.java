package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IInventoryService {
    public List<Inventory> getAllInventories();

    public Inventory getInventoryByID(int inventoryID) throws InventoryNotFoundException;

    public List<Inventory> getInventoriesByVaccine(Vaccine vaccine);

    public void saveInventory(Inventory inventory);

    public void updateInventory(Inventory updatedInventory);

    public void deleteInventory(int inventoryID) throws InventoryNotFoundException;
}
