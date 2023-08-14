package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.service.impl.VaccineTypeService;
import com.example.vaccination_management.validation.VaccineTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/vaccineTypes")
public class VaccineTypeController {
    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private VaccineTypeValidator vaccineTypeValidator;

    /**
     * HuyLVN
     * display all vaccine types information on the Vaccine Types page
     */
    @GetMapping("")
    public String getVaccinesDeleteFlagFalse(Model model) {
        List<VaccineType> vaccineTypeList = vaccineTypeService.getVaccinesTypeDeleteFlagFalse();

        model.addAttribute("vaccineTypeList", vaccineTypeList);

        return "/admin/VaccineType/VaccineTypeManager";
    }

    /**
     * HuyLVN
     * display all vaccines after being temporarily deleted on the Recycle Bin page
     */
    @GetMapping("/recycleVaccineType")
    public String getVaccinesTypeDeleteFlagTrue(Model model) {
        List<VaccineType> recycleVaccineTypeList = vaccineTypeService.getVaccinesTypeDeleteFlagTrue();

        model.addAttribute("recycleVaccineTypeList", recycleVaccineTypeList);

        return "/admin/VaccineType/RecycleVaccineType";
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

        return "/admin/VaccineType/VaccinesByType";
    }

    /**
     * HuyLVN
     * go to the Add Vaccine Type page
     */
    @GetMapping("/newVaccineType")
    public String showNewForm(Model model) {
        model.addAttribute("newVaccineType", new VaccineType());

        return "/admin/VaccineType/NewVaccineTypeForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/saveVaccineType")
    public String addVaccineType(@Validated @ModelAttribute("newVaccineType") VaccineType newVaccineType, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        vaccineTypeValidator.validate(newVaccineType, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/admin/VaccineType/NewVaccineTypeForm";
        }

        try {
            vaccineTypeService.saveVaccineType(newVaccineType);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Loai vaccine thêm mới thất bại!");
            return "redirect:/admin/vaccineTypes";
        }

        redirectAttributes.addFlashAttribute("messages", "Loai vaccine thêm mới thành công!");
        return "redirect:/admin/vaccineTypes";
    }

    /**
     * HuyLVN
     * remove vaccine type from database
     */
    @GetMapping("/deleteVaccineType/{id}")
    public String deleteVaccineType(@PathVariable("id") int vaccineTypeID, RedirectAttributes redirectAttributes) {
        vaccineTypeService.deleteVaccineType(vaccineTypeID);

        redirectAttributes.addFlashAttribute("messages", "Loại vaccine đã được chuyển vào thùng rác!");

        return "redirect:/admin/vaccineTypes";
    }

    /**
     * HuyLVN
     * go to the Update Vaccine Type page
     */
    @GetMapping("/editVaccineType/{id}")
    public String showEditForm(@PathVariable("id") int vaccineTypeID, Model model, RedirectAttributes redirectAttributes) {
        try {
            VaccineType vaccineType = vaccineTypeService.getVaccineTypeById(vaccineTypeID);
            model.addAttribute("pageTitle", vaccineType.getName());
            model.addAttribute("vaccineType", vaccineType);

            return "/admin/VaccineType/UpdateVaccineTypeForm";
        } catch (VaccineTypeNoFoundException e) {
            redirectAttributes.addFlashAttribute("messages", "Cập nhật thông tin loại vaccine thành công");

            return "redirect:/admin/vaccineTypes";
        }
    }

    /**
     * HuyLVN
     * remove vaccine type from database in Recycle Bin page
     */
    @GetMapping("/recycleVaccineType/destroyVaccineType/{id}")
    public String destroyVaccine(@PathVariable("id") int vaccineTypeID, RedirectAttributes redirectAttributes) {
        try {
            vaccineTypeService.destroyVaccineType(vaccineTypeID);
        } catch (VaccineTypeNoFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "Loại vaccine đã được xóa khỏi CSDL!");
        return "redirect:/admin/vaccineTypes/recycleVaccineType";
    }

    /**
     * HuyLVN
     * restore vaccine type in Recycle Bin page
     */
    @GetMapping("/recycleVaccineType/restoreVaccineType/{id}")
    public String restoreVaccine(@PathVariable("id") int vaccineTypeID, RedirectAttributes redirectAttributes) {
        vaccineTypeService.restoreVaccineType(vaccineTypeID);

        redirectAttributes.addFlashAttribute("messages", "Khôi phục loại vaccine thành công!");

        return "redirect:/admin/vaccineTypes/recycleVaccineType";
    }
}
