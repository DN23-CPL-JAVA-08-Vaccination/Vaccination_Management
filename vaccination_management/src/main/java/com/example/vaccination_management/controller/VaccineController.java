package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.service.impl.InventoryService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.service.impl.VaccineTypeService;
import com.example.vaccination_management.validation.VaccineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/admin/vaccines")
@Controller
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private VaccineValidator vaccineValidator;

    /**
     * HuyLVN
     * display all vaccines information on the Vaccines page
     */
    @GetMapping("")
    public String getVaccinesDeleteFlagFalse(Model model) {
        List<Vaccine> vaccinesList = vaccineService.getVaccinesDeleteFlagFalse();

        model.addAttribute("vaccinesList", vaccinesList);

        return "/admin/Vaccines/VaccinesManager";
    }

    /**
     * HuyLVN
     * display all vaccines after being temporarily deleted on the Recycle Bin page
     */
    @GetMapping("/recycleVaccine")
    public String getVaccinesDeleteFlagTrue(Model model) {
        List<Vaccine> recycleVaccineList = vaccineService.getVaccinesDeleteFlagTrue();

        model.addAttribute("recycleVaccineList", recycleVaccineList);

        return "/admin/Vaccines/RecycleVaccine";
    }

    /**
     * HuyLVN
     * go to the Add Vaccine page
     */
    @GetMapping("/newVaccine")
    public String showNewForm(Model model, ModelMap modelMap) {
        List<VaccineType> vaccineTypeList = vaccineTypeService.getAllVaccineType();

        model.addAttribute("newVaccine", new Vaccine());
        modelMap.addAttribute("typeList", vaccineTypeList);

        return "/admin/Vaccines/NewVaccineForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/newVaccine")
    public String addVaccine(@Validated @ModelAttribute("newVaccine") Vaccine newVaccine, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        vaccineValidator.validate(newVaccine, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", vaccineTypeService.getAllVaccineType());
            return "/admin/Vaccines/NewVaccineForm";
        }

        try {
            vaccineService.saveVaccine(newVaccine);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Thêm vaccine mới thất bại!");
            return "redirect:/admin/vaccines";
        }

        redirectAttributes.addFlashAttribute("messages", "Thêm vaccine thành công!");
        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * go to the Update Vaccine page
     */
    @GetMapping("/editVaccine/{id}")
    public String showUpdateForm(@PathVariable("id") int vaccineID, Model model, ModelMap modelMap) {
        try {
            List<VaccineType> vaccineTypeList = vaccineTypeService.getAllVaccineType();
            Vaccine vaccine = vaccineService.getVaccineByID(vaccineID);
            model.addAttribute("updateVaccine", vaccine);
            model.addAttribute("pageTitle", vaccine.getName());
            modelMap.addAttribute("typeList", vaccineTypeList);
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "/admin/Vaccines/UpdateVaccineForm";
    }

    /**
     * HuyLVN
     * get information from the form to update to the database
     */
    @PostMapping("/editVaccine")
    public String updateVaccine(@Validated @ModelAttribute("updateVaccine") Vaccine updatedVaccine, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        vaccineValidator.validate(updatedVaccine, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", vaccineTypeService.getAllVaccineType());
            return "/admin/Vaccines/UpdateVaccineForm";
        }

        try {
            vaccineService.updateVaccine(updatedVaccine);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Cập nhật vaccine thất bại!");
            return "redirect:/admin/vaccines";
        }

        redirectAttributes.addFlashAttribute("messages", "Cập nhật vaccine thành công");
        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * remove vaccine from database in Recycle Bin page
     */
    @GetMapping("/recycleVaccine/destroyVaccine/{id}")
    public String destroyVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        try {
            vaccineService.destroyVaccine(vaccineID);
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "The vaccine has been destroyed successfully");
        return "redirect:/admin/vaccines/recycleVaccine";
    }

    /**
     * HuyLVN
     * temporary removal of vaccines in Vaccines page
     */
    @GetMapping("/deleteVaccine/{id}")
    public String deleteVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        vaccineService.deleteVaccine(vaccineID);

        redirectAttributes.addFlashAttribute("messages", "The vaccine has been deleted successfully");

        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * restore vaccines in Recycle Bin page
     */
    @GetMapping("/recycleVaccine/restoreVaccine/{id}")
    public String restoreVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        vaccineService.restoreVaccine(vaccineID);

        redirectAttributes.addFlashAttribute("messages", "The vaccine has been restore successfully");

        return "redirect:/admin/vaccines/recycleVaccine";
    }

    /**
     * HuyLVN
     * detailed display of vaccine information
     */
    @GetMapping("/{id}")
    public String vaccineDetail(@PathVariable("id") int vaccineID, Model model, ModelMap modelMap) {
        try {
            Vaccine vaccine = vaccineService.getVaccineByID(vaccineID);
            List<VaccineType> vaccineTypeList = vaccineTypeService.getAllVaccineType();
            List<Inventory> inventoryList = inventoryService.getInventoriesByVaccine(vaccine);

            model.addAttribute("inventoryList", inventoryList);
            model.addAttribute("vaccine", vaccine);
            model.addAttribute("pageTitle", vaccine.getName());
            modelMap.addAttribute("typeList", vaccineTypeList);
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "/admin/Vaccines/VaccineDetail";
    }
}
