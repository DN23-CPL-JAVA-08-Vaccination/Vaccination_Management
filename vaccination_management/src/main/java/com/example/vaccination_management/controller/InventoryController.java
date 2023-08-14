package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.exception.InventoryNotFoundException;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.service.impl.InventoryService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.validation.InventoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private InventoryValidator inventoryValidator;

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

        return "admin/Inventory/NewInventoryForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/admin/vaccines/saveInventory")
    public String saveInventory(@Validated @ModelAttribute("newInventory") Inventory inventory, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        inventoryValidator.validate(inventory, bindingResult);

        if (bindingResult.hasErrors()) {
            try {
                Vaccine vaccine = vaccineService.getVaccineByID(inventory.getVaccine().getId());
                model.addAttribute("vaccine", vaccine);
                return "admin/Inventory/NewInventoryForm";
            } catch (VaccineNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            inventoryService.saveInventory(inventory);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Thêm lô hàng thất bại!");
            return "redirect:/admin/vaccines/" + inventory.getVaccine().getId();
        }

        redirectAttributes.addFlashAttribute("messages", "Thêm lô hàng thành công!");

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

        return "admin/Inventory/UpdateInventoryForm";
    }

    /**
     * HuyLVN
     * get information from the form to update to the database
     */
    @PostMapping("/admin/vaccines/updateInventory")
    public String updateInventory(@Validated @ModelAttribute("updateInventory") Inventory updatedInventory, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        inventoryValidator.validate(updatedInventory, bindingResult);

        if (bindingResult.hasErrors()) {
            try {
                Vaccine vaccine = vaccineService.getVaccineByID(updatedInventory.getVaccine().getId());
                model.addAttribute("vaccine", vaccine);
                return "admin/Inventory/UpdateInventoryForm";
            } catch (VaccineNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            inventoryService.updateInventory(updatedInventory);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Lô hàng cập nhật thất bại!");

            return "redirect:/admin/vaccines/" + updatedInventory.getVaccine().getId();
        }


        redirectAttributes.addFlashAttribute("messages", "Lô hàng cập nhật thành công!");

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

        redirectAttributes.addFlashAttribute("messages", "Lô hàng đã được chuyển vào kho lô hàng!");

        return "redirect:/admin/vaccines/" + vaccineID;
    }

    /**
     * HuyLVN
     * show inventory of vaccine
     */
    @GetMapping("/admin/vaccines/recycleInventory/{id}")
    public String RecycleInventory(@PathVariable("id") int vaccineID, Model model, RedirectAttributes redirectAttributes) {
        try {
            Vaccine vaccine = vaccineService.getVaccineByID(vaccineID);
            List<Inventory> inventoryList = inventoryService.getInventoryInRecycle(vaccine);

            model.addAttribute("vaccine", vaccine);
            model.addAttribute("inventoryList", inventoryList);
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "admin/Inventory/RecycleInventory";
    }

    @GetMapping("/admin/vaccines/recycleInventory/destroyInventory/{id}")
    public String destroyVaccine(@PathVariable("id") int inventoryID, RedirectAttributes redirectAttributes) {
        int vaccineID = 0;
        try {
            Inventory inventory = inventoryService.getInventoryByID(inventoryID);
            vaccineID = inventory.getVaccine().getId();

            inventoryService.destroyInventory(inventoryID);
        } catch (InventoryNotFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "Lô hàng đã được xóa khỏi CSDL!");
        return "redirect:/admin/vaccines/recycleInventory/" + vaccineID;
    }
}
