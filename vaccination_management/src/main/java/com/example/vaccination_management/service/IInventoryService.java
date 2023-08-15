package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;

import java.util.List;

public interface IInventoryService {
    /**
     * HuyLVN
     * get all information of inventories has delete flag is false, admin after login
     */
    public List<Inventory> getAllInventories();

    /**
     * HuyLVN
     * get all information of inventories has delete flag is true, admin after login
     */
    public List<Inventory> getInventoryInRecycle(Vaccine vaccine);

    /**
     * HuyLVN
     * get information of inventory by ID, admin after login
     */
    public Inventory getInventoryByID(int inventoryID) throws InventoryNotFoundException;

    /**
     * HuyLVN
     * get all information of inventories of vaccine, admin after login
     */
    public List<Inventory> getInventoriesByVaccine(Vaccine vaccine);

    /**
     * HuyLVN
     * get the information entered from the form and create a new inventory, admin after login
     */
    public void saveInventory(Inventory inventory);

    /**
     * HuyLVN
     * update information of inventory, admin after login
     */
    public void updateInventory(Inventory updatedInventory);

    /**
     * HuyLVN
     * remove inventory from database, admin after login
     */
    public void destroyInventory(int inventoryID) throws InventoryNotFoundException;

    public void deleteInventory(int inventoryID);
}
