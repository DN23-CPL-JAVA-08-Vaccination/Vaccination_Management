package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.ILocationService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.validation.EditPatientValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    @Autowired
    EditPatientValidator editPatientValidator;

    @Autowired
    IEmailService iEmail;

    @Autowired
    ILocationService iLocation;


    /**
     * TLINH
     * view form requires account
     */
    @GetMapping("/requires_account")
    public String viewFormRequires(ModelMap model, HttpServletRequest request){
        String message=request.getParameter("message");
        List<Location> listLocation=iLocation.findAll();
        model.addAttribute("listLocation", listLocation);
        model.addAttribute("message",message);
        model.addAttribute("patient", new PatientDTO());
        return "user/requires_account";
    }


    /**
     * TLINH
     * Insert information patient
     */
    @PostMapping("/insert_patient")
    public String insertPatient(@ModelAttribute("patientDTO") PatientDTO patientDTO,
                                RedirectAttributes redirectAttributes){
        String addressNew=patientDTO.getAddress()+" Quận "+patientDTO.getLocation();
        patientDTO.setDeleteFlag(false);
        patientDTO.setAddress(addressNew);
        patientDTO.setAccount(iAccount.findLatestAccountId());
      iPatient.insertPatient(patientDTO.getName(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getAddress(),patientDTO.getBirthday(),patientDTO.getHealthInsurance(),patientDTO.getGuardianName(),
              patientDTO.getGuardianPhone(),patientDTO.getDeleteFlag(),patientDTO.getAccount());
        redirectAttributes.addAttribute("message", "Gửi yêu cầu tài khoản tiêm chủng thành công!!");
      return "redirect:/patient/requires_account";
    }


    /**
     * TLINH
     * View detail patient
     */
    @GetMapping("/detail/{id}")
    public String patientDetail(Model model,
                                @PathVariable("id") Integer id){

//        Patient patient=iPatient.getById(id);
        Optional<Patient> op=iPatient.findById(id);
        PatientDTO patientDTO=new PatientDTO();
        Patient patient=op.get();
        BeanUtils.copyProperties(patient,patientDTO);
        patientDTO.setPhone(patient.getPhoneNumber());
        patientDTO.setBirthday(LocalDate.parse(patient.getBirthday()));



//         Tính toán độ tuổi
        LocalDate birthday = LocalDate.parse(patient.getBirthday());
        LocalDate now = LocalDate.now();
        Period agePeriod = Period.between(birthday, now);
        int age;
        String ageUnit;
        if (agePeriod.getYears() > 0) {
            age = agePeriod.getYears();
            ageUnit = "tuổi";
        } else if ((agePeriod.getYears()<0)&&(agePeriod.getMonths()>0)){
            age = agePeriod.getMonths();
            ageUnit = "tháng";
        }else {
            age=agePeriod.getDays();
            ageUnit="ngày tuổi";
        }

        model.addAttribute("patient", patientDTO);
        model.addAttribute("age", age);
        model.addAttribute("ageUnit", ageUnit);
        return "admin/Patient/patient_detail";
    }


    /**
     * TLINH
     * view form edit by id patient
     */
    @GetMapping("/form_edit/{id}")
    public ModelAndView viewFormEdit(ModelMap model,
                                     @PathVariable("id") Integer id){
        Optional<Patient> patient=iPatient.findById(id);
        String email=patient.get().getAccount().getEmail();
        PatientDTO patientDTO=new PatientDTO();
        Patient entity = patient.get();
        BeanUtils.copyProperties(entity, patientDTO);
        patientDTO.setPhone(entity.getPhoneNumber());
        patientDTO.setEmail(email);
        patientDTO.setBirthday(LocalDate.parse(entity.getBirthday()));
        model.addAttribute("patient",patientDTO);
        return new ModelAndView("admin/Patient/patient_edit",model);
//        để đơn giản hóa việc truyền dữ liệu từ Controller đến view và  dễ dàng hiển thị các trang HTML động với dữ liệu từ phía Server.
    }



    /**
     * TLINH
     * save edited patient information
     */
    @GetMapping("/save")
    public ModelAndView save(ModelMap model,
                             @Valid @ModelAttribute("patient") PatientDTO patientDTO, BindingResult result){
        editPatientValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            return new ModelAndView("admin/Patient/patient_edit");
        }
        iPatient.upPatient(patientDTO.getName(), patientDTO.getBirthday(),patientDTO.getAddress(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getGuardianName(),patientDTO.getGuardianPhone(),patientDTO.getId());
        model.addAttribute("msg","Cập nhật thông tin bệnh nhân thành công!!!");
        return new ModelAndView("redirect:/patient/view_patient", model);
    }


    /**
     * TLINH
     * view patient information is stored
     */
    @GetMapping("/view_patient_disable")
    public String viewPatientDisable(Model model, HttpServletRequest request,
                                     @RequestParam(required = false, defaultValue = "") String username,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "4") int size){
        String msg=request.getParameter("msg");
        Pageable pageable= PageRequest.of(page,size, Sort.by("health_insurance").descending());
        List<Patient> patientList=iPatient.getPatientByPageAccountNull(username,username,username,pageable);
        long totalPatient=iPatient.getTotalPatientAccountNull(username,username,username);
        int totalPage= (int) Math.ceil((double) totalPatient/size);
        String searchAction = "view_patient_disable";


        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", username);
        model.addAttribute("actionFlag",searchAction);
        model.addAttribute("msg",msg);
        model.addAttribute("patientList",patientList);
        return "admin/Patient/patient";
    }


    /**
     * TLINH
     * view patient information
     */
    @GetMapping("/view_patient")
    public String view(Model model, HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "") String username,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "4") int size){
        String msg=request.getParameter("msg");
        Pageable pageable= PageRequest.of(page,size, Sort.by("health_insurance").descending());
        List<Patient> patientList=iPatient.getPatientByPage(username,username,username,false,pageable);
        long totalPatient=iPatient.getTotalPatient(username,username,username,false);
        int totalPage= (int) Math.ceil((double) totalPatient/size);
        String searchAction = "view_patient";


        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", username);
        model.addAttribute("actionFlag",searchAction);
        model.addAttribute("msg",msg);
        model.addAttribute("patientList",patientList);
        return "admin/Patient/patient";
    }


    /**
     * TLINH
     * view patient information is delete flag
     */
    @GetMapping("/view_patientDelete")
    public String viewDelete(Model model, HttpServletRequest request,
                             @RequestParam(required = false, defaultValue = "") String username,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "4") int size){
        String msg=request.getParameter("msg");
        Pageable pageable= PageRequest.of(page,size, Sort.by("health_insurance").descending());
        List<Patient> patientList=iPatient.getPatientByPage(username,username,username,true,pageable);
        long totalPatient=iPatient.getTotalPatient(username,username,username,true);
        int totalPage= (int) Math.ceil((double) totalPatient/size);
        String searchAction = "view_patientDelete";


        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", username);
        model.addAttribute("actionFlag",searchAction);
        model.addAttribute("msg",msg);
        model.addAttribute("patientList",patientList);
        return "admin/Patient/patient";
    }


    /**
     * TLINH
     * Delete flag / restore patient by id
     */
    @GetMapping("/delete_flag/{id}/{deleteFlag}")
    public ModelAndView deleteFlag(@PathVariable("id") Integer id,
                             @PathVariable("deleteFlag") Boolean deleteFlag){
        Optional<Patient> patient= iPatient.findById(id);
        IAccountDetailDTO detailDTO=iAccount.findAccountById(patient.get().getAccount().getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/patient/view_patient");
        iPatient.updateDeleteFlagById(deleteFlag,id);
        if (deleteFlag){
            iAccount.updateEnableFlagById(false,patient.get().getAccount().getId());
            Boolean isEmail = iEmail.SendEmailDeactivate(detailDTO);
            modelAndView.addObject("msg", "Xóa thông tin bệnh nhân thành công!!");
        }else {
            modelAndView.addObject("msg", "Khôi phục thông tin bệnh nhân thành công!!");
        }
        return modelAndView;
    }

    /**
     * TLINH
     * Storage patient by id
     */
    @GetMapping("/storage/{id}")
    public ModelAndView storage(@PathVariable("id") Integer id){
        ModelAndView modelAndView=new ModelAndView("redirect:/patient/view_patient");
        Optional<Patient> patient=iPatient.findById(id);
        iAccount.deleteById(patient.get().getAccount().getId());
        LocalDate currentDate=LocalDate.now();
        modelAndView.addObject("msg","Lưu trữ thông tin bệnh nhân thành công");
        return modelAndView;
    }


}
