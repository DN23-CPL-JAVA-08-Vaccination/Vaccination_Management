package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.service.impl.InventoryService;
import com.example.vaccination_management.service.impl.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private VaccineService vaccineService;

    /**
     * HuyLVN
     * go to the Add Inventory page
     */
    @GetMapping("/admin/vaccines/newInventory/{id}")
    public String showNewInventoryForm(@PathVariable("id") int vaccineID, Model model) {
        try {
            Vaccine vaccine = vaccineService.getVaccineByID(vaccineID);

            model.addAttribute("vaccine", vaccine);
            model.addAttribute("newInventory", new Inventory());
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "Admin/Inventory/NewInventoryForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/admin/vaccines/saveInventory")
    public String saveInventory(Inventory inventory, RedirectAttributes redirectAttributes) {
        inventoryService.saveInventory(inventory);

        redirectAttributes.addFlashAttribute("messages", "The inventory has been created successfully");

        return "redirect:/admin/vaccines/" + inventory.getVaccine().getId();
    }

    /**
     * HuyLVN
     * go to the Update Inventory page
     */
    @GetMapping("/admin/vaccines/editInventory/{id}")
    public String showUpdateForm(@PathVariable("id") int inventoryID, Model model) {
        try {
            Inventory inventory = inventoryService.getInventoryByID(inventoryID);
            Vaccine vaccine = vaccineService.getVaccineByID(inventory.getVaccine().getId());

            model.addAttribute("updateInventory", inventory);
            model.addAttribute("pageTitle", vaccine.getName());
            model.addAttribute("vaccineID", inventory.getVaccine().getId());
        } catch (InventoryNotFoundException | VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "Admin/Inventory/UpdateInventoryForm";
    }

    /**
     * HuyLVN
     * get information from the form to update to the database
     */
    @PostMapping("/admin/vaccines/updateInventory")
    public String updateInventory(Inventory updatedInventory, RedirectAttributes redirectAttributes) {
        inventoryService.updateInventory(updatedInventory);

        redirectAttributes.addFlashAttribute("messages", "The inventory has been updated successfully");

        return "redirect:/admin/vaccines/" + updatedInventory.getVaccine().getId();
    }

    /**
     * HuyLVN
     * remove inventory from database in Vaccine Detail page
     */
    @GetMapping("/admin/vaccines/deleteInventory/{id}")
    public String deleteInventory(@PathVariable("id") int inventoryID, RedirectAttributes redirectAttributes) {
        int vaccineID = 0;
        try {
            Inventory inventory = inventoryService.getInventoryByID(inventoryID);
            vaccineID = inventory.getVaccine().getId();

            inventoryService.deleteInventory(inventoryID);
        } catch (InventoryNotFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "The inventory has been destroyed successfully");

        return "redirect:/admin/vaccines/" + vaccineID;
    }
}
