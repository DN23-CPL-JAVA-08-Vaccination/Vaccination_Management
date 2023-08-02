package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.repository.IInventoryRepository;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.service.IInventoryService;
import com.example.vaccination_management.service.IVaccineService;
import jdk.tools.jlink.internal.Platform;
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

    @Override
    public List<Inventory> getAllInventories() {
        return iInventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryByID(int inventoryID) throws InventoryNotFoundException {
        Optional<Inventory> inventory = iInventoryRepository.findById(inventoryID);

        if (inventory.isPresent()) {
            return inventory.get();
        }

        throw new InventoryNotFoundException("Couldn't find any inventories with ID");
    }

    @Override
    public List<Inventory> getInventoriesByVaccine(Vaccine vaccine) {
        return iInventoryRepository.getInventoriesByVaccine(vaccine);
    }

    @Override
    public void saveInventory(Inventory inventory) {
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        inventory.setCreatedDate(createDate.format(formatter));
        inventory.setUpdatedDate(updateDate.format(formatter));

        iInventoryRepository.save(inventory);
    }

    @Override
    public void updateInventory(Inventory updatedInventory) {
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        updatedInventory.setUpdatedDate(updateDate.format(formatter));

        iInventoryRepository.save(updatedInventory);
    }

    @Override
    public void deleteInventory(int inventoryID) throws InventoryNotFoundException {
        Long count = iInventoryRepository.countById(inventoryID);

        if (count == null || count == 0) {
            throw new InventoryNotFoundException("Couldn't find any inventories with ID");
        }

        iInventoryRepository.deleteById(inventoryID);
    }
}
