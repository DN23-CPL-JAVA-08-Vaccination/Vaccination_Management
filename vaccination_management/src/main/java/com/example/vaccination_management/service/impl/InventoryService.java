package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;
import com.example.vaccination_management.repository.IInventoryRepository;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService {
    @Autowired
    private IInventoryRepository iInventoryRepository;

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private VaccineService vaccineService;

    /**
     * HuyLVN
     * get all information of inventories, admin after login
     */
    @Override
    public List<Inventory> getAllInventories() {
        return iInventoryRepository.findAll();
    }

    /**
     * HuyLVN
     * get information of inventory by ID, admin after login
     */
    @Override
    public Inventory getInventoryByID(int inventoryID) throws InventoryNotFoundException {
        Optional<Inventory> inventory = iInventoryRepository.findById(inventoryID);

        if (inventory.isPresent()) {
            return inventory.get();
        }

        throw new InventoryNotFoundException("Couldn't find any inventories with ID");
    }

    /**
     * HuyLVN
     * get all information of inventories of vaccine, admin after login
     */
    @Override
    public List<Inventory> getInventoriesByVaccine(Vaccine vaccine) {
        return iInventoryRepository.getInventoriesByVaccine(vaccine);
    }

    /**
     * HuyLVN
     * get the information entered from the form and create a new inventory, admin after login
     */
    @Override
    public void saveInventory(Inventory inventory) {
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        inventory.setCreatedDate(createDate.format(formatter));
        inventory.setUpdatedDate(updateDate.format(formatter));

        iInventoryRepository.save(inventory);
    }

    /**
     * HuyLVN
     * update information of inventory, admin after login
     */
    @Override
    public void updateInventory(Inventory updatedInventory) {
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        updatedInventory.setUpdatedDate(updateDate.format(formatter));

        iInventoryRepository.save(updatedInventory);
    }

    /**
     * HuyLVN
     * remove inventory from database, admin after login
     */
    @Override
    public void deleteInventory(int inventoryID) throws InventoryNotFoundException {
        Long count = iInventoryRepository.countById(inventoryID);

        if (count == null || count == 0) {
            throw new InventoryNotFoundException("Couldn't find any inventories with ID");
        }

        iInventoryRepository.deleteById(inventoryID);
    }

    /**
     * QuangVt
     * Update inventory quantity vaccine when completed vaccination
     */
    @Override
    public void updateInventoryQuantity(Integer vaccine_id){
        iInventoryRepository.updateInventoryQuantity(vaccine_id);
    }

}
