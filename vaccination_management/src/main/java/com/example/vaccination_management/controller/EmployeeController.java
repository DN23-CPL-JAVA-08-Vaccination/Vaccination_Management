package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;

import com.example.vaccination_management.service.IEmployeeService;
import com.example.vaccination_management.service.IPositionService;
import com.example.vaccination_management.validation.EmployeeCreateValidator;
import com.example.vaccination_management.validation.EmployeeEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/admin/employee")
@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private EmployeeCreateValidator employeeCreateValidator;

    @Autowired
    private EmployeeEditValidator employeeEditValidator;



    String currentEmail = null;
    String currentPhone = null;
    String currentIdCard = null;

    /**
     * ThangLV
     * get list Employee by Employee's name
     */
    @GetMapping("")
    public String listEmPloyee(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "4") int size,
                               @RequestParam(required = false, defaultValue = "") String searchName,
                               Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        List<EmployeeListDTO> employeeListDTOS = employeeService.getEmployeeByPage('%' + searchName + '%', pageable);
        model.addAttribute("employeeListDTOS", employeeListDTOS);
        long totalEmployee = employeeService.getTotalEmployee('%' + searchName + '%');
        int totalPages = (int) Math.ceil((double) totalEmployee / size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchName", searchName);

        model.addAttribute("currentPage", page);
        return "admin/employee/list";
    }


    /**
     * ThangLV
     * show form Create Employee
     */
    @GetMapping("/create")
    public String showFormCreate(Model model) {
        model.addAttribute("employeeCreateDTO", new EmployeeCreateDTO());
        model.addAttribute("positionList", positionService.findAll());
        return "admin/employee/create";
    }

    /**
     * ThangLV
     * get Data in form use create Employee
     */
    @PostMapping("/create")
    public String save(@Validated @ModelAttribute EmployeeCreateDTO employeeCreateDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        employeeCreateValidator.validate(employeeCreateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("positionList", positionService.findAll());
            model.addAttribute("msg", "Vui lòng nhập đúng các trường !");
            return "admin/employee/create";
        }
        try {
            employeeService.create(employeeCreateDTO);

        } catch (Exception ex) {
            attributes.addFlashAttribute("msg", "Thêm mới thất bại !");
            return "redirect:/admin/employee";
        }
        attributes.addFlashAttribute("msg", "Thêm mới thành công !");
        return "redirect:/admin/employee";
    }

    /**
     * ThangLV
     * show form update information employee
     */
    @GetMapping("/update")
    public String showFormUpdate(@RequestParam int id, Model model) {
        EmployeeCreateDTO employeeCreateDTO = employeeService.getInforUpdateById(id);
        if (employeeCreateDTO == null) {
            return "redirect:/admin/employee";
        }
        this.currentEmail = employeeCreateDTO.getEmail();
        this.currentIdCard = employeeCreateDTO.getIdCard();
        this.currentPhone = employeeCreateDTO.getPhone();
        model.addAttribute("employeeCreateDTO", employeeCreateDTO);
        model.addAttribute("positionList", positionService.findAll());
        return "admin/employee/update";
    }

    /**
     * ThangLV
     *  update information employee
     */
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute EmployeeCreateDTO employeeCreateDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        employeeCreateDTO.setCurrentEmail(this.currentEmail);
        employeeCreateDTO.setCurrentPhone(this.currentPhone);
        employeeCreateDTO.setCurrentIdCard(this.currentIdCard);
        employeeEditValidator.validate(employeeCreateDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("positionList", positionService.findAll());
            model.addAttribute("msg", "Vui lòng nhập đúng các trường !");
            return "admin/employee/update";
        }

        try {
            employeeService.update(employeeCreateDTO);
        } catch (Exception e) {
            attributes.addFlashAttribute("msg", "Cập nhật thất bại !");
            return "redirect:/admin/employee";

        }
        attributes.addFlashAttribute("msg", "Cập nhật thành công !");
        return "redirect:/admin/employee";
    }

    /**
     * ThangLV
     * delete logic information employee
     */
    @GetMapping("/delete")
    public String delete(@RequestParam int id,RedirectAttributes attributes) {

        try {
            employeeService.delete(id);
        }catch (Exception e){
            attributes.addFlashAttribute("msg","Xoá thất bại !");
            return "redirect:/admin/employee";
        }
        attributes.addFlashAttribute("msg","Xoá thành công !");

        return "redirect:/admin/employee";
    }
}
