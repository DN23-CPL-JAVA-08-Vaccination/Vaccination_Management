package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;

import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.service.IEmployeeService;
import com.example.vaccination_management.service.IPositionService;
import com.example.vaccination_management.validation.EmployeeCreateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/admin/employee")
@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private EmployeeCreateValidator employeeCreateValidator;


    /**
     * ThangLV
     * get list Employee by Employee's name
     */
    @GetMapping("")
    public String showList(Model model,
                           @RequestParam(required = false, defaultValue = "") String searchName,
                           @RequestParam Optional<Integer> page) {
        int pageBegin = 0;
        if (page.isPresent()) {
            pageBegin = page.get();
        }
        Pageable pageable = PageRequest.of(pageBegin, 4, Sort.by("name").descending());

        Page<EmployeeListDTO> employeeListDTOS;

        employeeListDTOS = employeeService.searchByName('%' + searchName + '%', pageable);

        model.addAttribute("employeeListDTOS", employeeListDTOS);
        model.addAttribute("searchName", searchName);
        return "admin/employee/list";
    }

    /**
     * ThangLV
     * get information of employee , use Show
     */
    @GetMapping("/infor/{id}")
    public String getAttachFacility(Model model, @PathVariable int id) {
        InforEmployeeDTO employeeDTO = employeeService.getInforById(id);
        model.addAttribute("employeeDTO", employeeDTO);
        return "account_information";
    }


    /**
     * ThangLV
     * show form Create Employee
     */
    @GetMapping("/create")
    public String showFormCreate(Model model) {
        model.addAttribute("employeeDTO", new EmployeeCreateDTO());

        model.addAttribute("positionList", positionService.findAll());
        return "admin/employee/create";
    }

    /**
     * ThangLV
     * get Data in form use create Employee
     */
    @PostMapping("/create")
    public String save(@Validated @ModelAttribute EmployeeCreateDTO employeeDTO, BindingResult bindingResult) {
        employeeCreateValidator.validate(employeeDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/employee/create";
        }
        employeeService.create(employeeDTO);
        return "redirect:/employee/list";
    }


    @GetMapping("/update")
    public String showFormUpdate(@RequestParam int id, Model model) {
        model.addAttribute("employee", employeeService.getInforUpdateById(id));
        model.addAttribute("positionList", positionService.findAll());
        return "admin/employee/update";
    }

    @PostMapping("/update")
    public String update(@Validated @ModelAttribute EmployeeCreateDTO employeeDTO, @RequestParam String date, BindingResult bindingResult) {
//        employeeDTO.setBirthday(date);
//
//        if (bindingResult.hasErrors()) {
//            return "employee/create";
//        }
//        Customer customer = new Customer();
//        BeanUtils.copyProperties(employeeDTO, customer);
//        customerService.save(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        if (employeeService.delete(id)){

        }
        return "redirect:/customer/list";
    }
}
