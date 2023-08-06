package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.service.impl.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/vaccineTypes")
public class VaccineTypeController {
    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private VaccineService vaccineService;

    /**
     * HuyLVN
     * display all vaccine types information on the Vaccine Types page
     */
    @GetMapping("")
    public String getAllVaccineTypes(Model model) {
        List<VaccineType> vaccineTypeList = vaccineTypeService.getAllVaccineType();

        model.addAttribute("vaccineTypeList", vaccineTypeList);

        return "/Admin/VaccineType/VaccineTypeManager";
    }

    /**
     * HuyLVN
     * display all vaccines of vaccine type information on the page
     */
    @GetMapping("/{id}")
    public String getVaccineByVaccineType(@PathVariable("id") int vaccineTypeID, Model model) {
        try {
            VaccineType vaccineType = vaccineTypeService.getVaccineTypeById(vaccineTypeID);
            List<Vaccine> vaccinesByTypeList = vaccineService.getVaccinesByVaccineType(vaccineType);

            model.addAttribute("id", vaccineTypeID);
            model.addAttribute("vaccinesByTypeList", vaccinesByTypeList);
            model.addAttribute("pageTitle", vaccineType.getName());
        } catch (VaccineTypeNoFoundException e) {
            e.printStackTrace();
        }

        return "/Admin/VaccineType/VaccinesByType";
    }

    /**
     * HuyLVN
     * go to the Add Vaccine Type page
     */
    @GetMapping("/newVaccineType")
    public String showNewForm(Model model) {
        model.addAttribute("newVaccineType", new VaccineType());

        return "/Admin/VaccineType/NewVaccineTypeForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/saveVaccineType")
    public String addVaccineType(VaccineType newVaccineType, RedirectAttributes redirectAttributes) {
        vaccineTypeService.saveVaccineType(newVaccineType);
        redirectAttributes.addFlashAttribute("messages", "The vaccine type has been saved successfully");

        return "redirect:/admin/vaccineTypes";
    }

    /**
     * HuyLVN
     * remove vaccine type from database
     */
    @GetMapping("/deleteVaccineType/{id}")
    public String deleteVaccineType(@PathVariable("id") int vaccineTypeID, RedirectAttributes redirectAttributes) {
        try {
            vaccineTypeService.deleteVaccineType(vaccineTypeID);
        } catch (VaccineTypeNoFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "The vaccine type has been deleted successfully");

        return "redirect:/admin/vaccineTypes";
    }

    /**
     * HuyLVN
     * go to the Update Vaccine page
     */
    @GetMapping("/editVaccineType/{id}")
    public String showEditForm(@PathVariable("id") int vaccineTypeID, Model model, RedirectAttributes redirectAttributes) {
        try {
            VaccineType vaccineType = vaccineTypeService.getVaccineTypeById(vaccineTypeID);
            model.addAttribute("pageTitle", vaccineType.getName());
            model.addAttribute("vaccineType", vaccineType);

            return "/Admin/VaccineType/UpdateVaccineTypeForm";
        } catch (VaccineTypeNoFoundException e) {
            redirectAttributes.addFlashAttribute("messages", "The vaccine type has been updated successfully");

            return "redirect:/admin/vaccineTypes";
        }
    }
}
