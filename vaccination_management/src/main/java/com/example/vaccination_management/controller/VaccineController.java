package com.example.vaccination_management.controller;


import com.example.vaccination_management.entity.Inventory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.service.IVaccineTypeService;
import com.example.vaccination_management.service.impl.InventoryService;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.service.IVaccineService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.validation.VaccineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.repository.IVaccineTypeRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

@RequestMapping("/")
@Controller
public class VaccineController {
    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private IVaccineTypeService vaccineTypeService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Autowired
    private VaccineValidator vaccineValidator;

    /**
     * HuyLVN
     * display all vaccines information on the Vaccines page
     */
    @GetMapping("/admin/vaccines")
    public String getVaccinesDeleteFlagFalse(Model model) {
        List<Vaccine> vaccinesList = vaccineService.getVaccinesDeleteFlagFalse();

        model.addAttribute("vaccinesList", vaccinesList);

        return "/admin/Vaccines/VaccinesManager";
    }

    /**
     * HuyLVN
     * display all vaccines after being temporarily deleted on the Recycle Bin page
     */
    @GetMapping("/admin/vaccines/recycleVaccine")
    public String getVaccinesDeleteFlagTrue(Model model) {
        List<Vaccine> recycleVaccineList = vaccineService.getVaccinesDeleteFlagTrue();

        model.addAttribute("recycleVaccineList", recycleVaccineList);

        return "/admin/Vaccines/RecycleVaccine";
    }

    /**
     * HuyLVN
     * go to the Add Vaccine page
     */
    @GetMapping("/admin/vaccines/newVaccine")
    public String showNewForm(Model model, ModelMap modelMap) {
        List<VaccineType> vaccineTypeList = vaccineTypeService.getVaccinesTypeDeleteFlagFalse();

        model.addAttribute("newVaccine", new Vaccine());
        modelMap.addAttribute("typeList", vaccineTypeList);

        return "/admin/Vaccines/NewVaccineForm";
    }

    /**
     * HuyLVN
     * get information from the form to save to the database
     */
    @PostMapping("/admin/vaccines/newVaccine")
    public String addVaccine(@Validated @ModelAttribute("newVaccine") Vaccine newVaccine, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        vaccineValidator.validate(newVaccine, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", vaccineTypeService.getVaccinesTypeDeleteFlagFalse());
            return "/admin/Vaccines/NewVaccineForm";
        }

        try {
            vaccineService.saveVaccine(newVaccine);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Vaccine thêm mới thất bại!");
            return "redirect:/admin/vaccines";
        }

        redirectAttributes.addFlashAttribute("messages", "Vaccine thêm mới thành công!");
        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * go to the Update Vaccine page
     */
    @GetMapping("/admin/vaccines/editVaccine/{id}")
    public String showUpdateForm(@PathVariable("id") int vaccineID, Model model, ModelMap modelMap) {
        try {
            List<VaccineType> vaccineTypeList = vaccineTypeService.getVaccinesTypeDeleteFlagFalse();
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

    @PostMapping("/admin/vaccines/editVaccine")
    public String updateVaccine(@Validated @ModelAttribute("updateVaccine") Vaccine updatedVaccine, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        vaccineValidator.validate(updatedVaccine, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", vaccineTypeService.getVaccinesTypeDeleteFlagFalse());
            return "/admin/Vaccines/UpdateVaccineForm";
        }

        try {
            vaccineService.updateVaccine(updatedVaccine);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messages", "Cập nhật thông tin vaccine thất bại!");
            return "redirect:/admin/vaccines";
        }

        redirectAttributes.addFlashAttribute("messages", "Cập nhật thông tin vaccine thành công!");
        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * remove vaccine from database in Recycle Bin page
     */
    @GetMapping("/admin/vaccines/recycleVaccine/destroyVaccine/{id}")
    public String destroyVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        try {
            vaccineService.destroyVaccine(vaccineID);
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("messages", "Vaccine đã được xóa khỏi CSDL!");
        return "redirect:/admin/vaccines/recycleVaccine";
    }

    /**
     * HuyLVN
     * temporary removal of vaccines in Vaccines page
     */
    @GetMapping("/admin/vaccines/deleteVaccine/{id}")
    public String deleteVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        vaccineService.deleteVaccine(vaccineID);

        redirectAttributes.addFlashAttribute("messages", "Vaccine đã được chuyển vào thùng rác!");

        return "redirect:/admin/vaccines";
    }

    /**
     * HuyLVN
     * restore vaccines in Recycle Bin page
     */
    @GetMapping("/admin/vaccines/recycleVaccine/restoreVaccine/{id}")
    public String restoreVaccine(@PathVariable("id") int vaccineID, RedirectAttributes redirectAttributes) {
        vaccineService.restoreVaccine(vaccineID);

        redirectAttributes.addFlashAttribute("messages", "Khôi phục vaccine thành công!");

        return "redirect:/admin/vaccines/recycleVaccine";
    }

    /**
     * HuyLVN
     * detailed display of vaccine information
     */
    @GetMapping("/admin/vaccines/{id}")
    public String vaccineDetail(@PathVariable("id") int vaccineID, Model model, ModelMap modelMap) {
        try {
            Vaccine vaccine = vaccineService.getVaccineByID(vaccineID);
            List<Inventory> inventoryList = inventoryService.getInventoriesByVaccine(vaccine);

            NumberFormat formatter = new DecimalFormat("#0");

            model.addAttribute("price", formatter.format(vaccine.getPrice()) + " VNĐ");
            model.addAttribute("inventoryList", inventoryList);
            model.addAttribute("vaccine", vaccine);
            model.addAttribute("pageTitle", vaccine.getName());
        } catch (VaccineNotFoundException e) {
            e.printStackTrace();
        }

        return "/admin/Vaccines/VaccineDetail";
    }


    /**
     * QuangVT
     * get information of vaccine
     */
    @GetMapping("/doctor/vaccine")
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccineDTO> vaccinePage = vaccineService.searchVaccine(pageable, '%' + strSearch + '%');
        model.addAttribute("vaccineList", vaccinePage);
        return "doctors/vaccine";
    }

    /**
     * LoanHTP
     * Displays a paginated list of vaccination histories for a specific patient.
     */
    @GetMapping("/vaccine/list-vaccine")
    public String listVaccineAndSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "4") int size,
                                       @RequestParam(value = "search", required = false) String searchQuery,
                                       Model model) {
        List<Vaccine> vaccines;
        long totalVaccines;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            vaccines = vaccineService.getVaccinesByPageAndSearch(page, size, searchQuery);
            totalVaccines = vaccineService.getTotalVaccinesBySearch(searchQuery);
        } else {
            vaccines = vaccineService.getVaccinesByPage(page, size);
            totalVaccines = vaccineService.getTotalVaccines();
        }

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        int totalPages = (int) Math.ceil((double) totalVaccines / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("currentPage", page);

        return "/user/vaccine/vaccine-vaccinetype-list";
    }

    /**
     * LoanHTP
     * Displays details of a specific vaccination history.
     */
    @GetMapping("/vaccine/list-vaccine/{vaccine_type_id}")
    public String getVaccineByVaccineTypeAndSearch(@PathVariable("vaccine_type_id") int vaccine_type_id,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "4") int size,
                                                   @RequestParam(value = "search", required = false) String searchQuery,
                                                   Model model) {
        VaccineType vaccineType = vaccineTypeService.findVaccineTypeById(vaccine_type_id);
        model.addAttribute("vaccine_type_id", vaccine_type_id);
//        model.addAttribute("vaccineTypeName", vaccineType.getName());

        List<Vaccine> vaccines;
        long totalVaccines;

        if (searchQuery != null) {
            vaccines = vaccineService.getVaccinesByPageAndVaccineTypeAndSearch(page, size, vaccineType, searchQuery);
            totalVaccines = vaccineService.getTotalVaccinesByVaccineTypeAndSearch(vaccineType, searchQuery);
        } else {
            vaccines = vaccineService.getVaccinesByPageAndVaccineType(page, size, vaccineType);
            totalVaccines = vaccineService.getTotalVaccinesByVaccineType(vaccineType);
        }

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        int totalPages = (int) Math.ceil((double) totalVaccines / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("currentPage", page);

        return "/user/vaccine/vaccine-vaccinetype-list";
    }

}
