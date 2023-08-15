package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.ILocationService;

import com.example.vaccination_management.validation.EditPatientValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.Period;

import java.util.List;
import java.util.Optional;


@Controller
public class PatientController {

    @Autowired
    IPatientService iPatientService;
    @Autowired
    IVaccinationHistoryService iVaccinationHistoryService;

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

    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private IPatientService patientService;

    @GetMapping("/doctor/patient")
    public String getAllPatient(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size , @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IPatientDTO> patientPage = iPatientService.getPatients(pageable,'%' +strSearch + '%');
        model.addAttribute("patientList", patientPage);
        return "doctors/patient";
    }

    @GetMapping("/doctor/patient/view")
    public String detailPatient(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "", required = false) String strSearch,
                                @RequestParam Integer id) {

        Patient patient = iPatientService.getPatientById(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastTime").descending());
        Page<IVaccinationHistoryDTO>  list = iVaccinationHistoryService.getVaccinationByPatient(id,pageable);
        model.addAttribute("patientID",id);
        model.addAttribute("patient",patient);
        model.addAttribute("vaccinationList", list);
        return "doctors/detailpatient";
    }


    /**
     * TLINH
     * view form requires account
     */
    @GetMapping("/patient/requires_account")
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
    @PostMapping("/patient/insert_patient")
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
    @GetMapping("/admin/patient/detail/{id}")
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
    @GetMapping("/admin/patient/form_edit/{id}")
    public ModelAndView viewFormEdit(ModelMap model,
                                     @PathVariable("id") Integer id){
        Optional<Patient> patient=iPatient.findById(id);
        String email=patient.get().getAccount().getEmail();
        PatientDTO patientDTO=new PatientDTO();
        Patient entity = patient.get();
        BeanUtils.copyProperties(entity, patientDTO);
        String address = entity.getAddress();
        String regex = "(.*?)\\sQuận"; // Regex để tìm phần trước chữ "Quận"
        String regex1 = "Quận\\s(.*)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Pattern pattern1 = java.util.regex.Pattern.compile(regex1);
        java.util.regex.Matcher matcher = pattern.matcher(address);
        java.util.regex.Matcher matcher1 = pattern1.matcher(address);

        String extractedAddressPart = "";
        if (matcher.find()) {
            extractedAddressPart = matcher.group(1); // Lấy phần trước chữ "Quận"
        }
        String extractedAddressPart1 = "";
        if (matcher1.find()) {
            extractedAddressPart1 = matcher1.group(1); // Lấy phần trước chữ "Quận"
        }
        patientDTO.setLocation(extractedAddressPart1); // Gán địa chỉ tách được vào patientDTO
        patientDTO.setAddress(extractedAddressPart); // Gán địa chỉ tách được vào patientDTO
        List<Location> listLocation=iLocation.findAll();

        patientDTO.setPhone(entity.getPhoneNumber());
        patientDTO.setEmail(email);
        patientDTO.setBirthday(LocalDate.parse(entity.getBirthday()));
        model.addAttribute("patient",patientDTO);
        model.addAttribute("listLocation", listLocation);
        return new ModelAndView("admin/Patient/patient_edit",model);
//        để đơn giản hóa việc truyền dữ liệu từ Controller đến view và  dễ dàng hiển thị các trang HTML động với dữ liệu từ phía Server.
    }



    /**
     * TLINH
     * save edited patient information
     */
    @GetMapping("/admin/patient/save")
    public ModelAndView save(ModelMap model,
                             @Valid @ModelAttribute("patient") PatientDTO patientDTO, BindingResult result){
        editPatientValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            List<Location> listLocation=iLocation.findAll();
            model.addAttribute("listLocation", listLocation);
            return new ModelAndView("admin/Patient/patient_edit");
        }
        String addressNew=patientDTO.getAddress()+" Quận "+patientDTO.getLocation();
        patientDTO.setAddress(addressNew);
        iPatient.upPatient(patientDTO.getName(), patientDTO.getBirthday(),patientDTO.getAddress(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getGuardianName(),patientDTO.getGuardianPhone(),patientDTO.getId());
        model.addAttribute("msg","Cập nhật thông tin bệnh nhân thành công!!!");
        return new ModelAndView("redirect:/admin/patient/view_patient", model);
    }


    /**
     * TLINH
     * view patient information is stored
     */
    @GetMapping("/admin/patient/view_patient_disable")
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
    @GetMapping("/admin/patient/view_patient")
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
    @GetMapping("/admin/patient/view_patientDelete")
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
    @GetMapping("/admin/patient/delete_flag/{id}/{deleteFlag}")
    public ModelAndView deleteFlag(@PathVariable("id") Integer id,
                             @PathVariable("deleteFlag") Boolean deleteFlag){
        Optional<Patient> patient= iPatient.findById(id);
        IAccountDetailDTO detailDTO=iAccount.findAccountById(patient.get().getAccount().getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/patient/view_patient");
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
    @GetMapping("/admin/patient/storage/{id}")
    public ModelAndView storage(@PathVariable("id") Integer id){
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/patient/view_patient");
        Optional<Patient> patient=iPatient.findById(id);
        iAccount.deleteById(patient.get().getAccount().getId());
        LocalDate currentDate=LocalDate.now();
        modelAndView.addObject("msg","Lưu trữ thông tin bệnh nhân thành công");
        return modelAndView;
    }


    @GetMapping("/user/patient/view_form_edit_patient")
    public ModelAndView viewFormEditInformation(){
        ModelAndView modelAndView = new ModelAndView("/user/patient/edit_patient");

        String username = accountDetailService.getCurrentUserName();

        Patient entity = patientService.findPatientByUsername(username);

        PatientDTO patientDTO=new PatientDTO();

        BeanUtils.copyProperties(entity, patientDTO);
        String address = entity.getAddress();
        String regex = "(.*?)\\sQuận"; // Regex để tìm phần trước chữ "Quận"
        String regex1 = "Quận\\s(.*)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Pattern pattern1 = java.util.regex.Pattern.compile(regex1);
        java.util.regex.Matcher matcher = pattern.matcher(address);
        java.util.regex.Matcher matcher1 = pattern1.matcher(address);

        String extractedAddressPart = "";
        if (matcher.find()) {
            extractedAddressPart = matcher.group(1); // Lấy phần trước chữ "Quận"
        }
        String extractedAddressPart1 = "";
        if (matcher1.find()) {
            extractedAddressPart1 = matcher1.group(1); // Lấy phần trước chữ "Quận"
        }
        patientDTO.setLocation(extractedAddressPart1); // Gán địa chỉ tách được vào patientDTO
        patientDTO.setAddress(extractedAddressPart); // Gán địa chỉ tách được vào patientDTO
        patientDTO.setPhone(entity.getPhoneNumber());
        patientDTO.setHealthInsurance(entity.getHealthInsurance());
        patientDTO.setEnableFlag(entity.getAccount().getEnableFlag());
        patientDTO.setEmail(entity.getAccount().getEmail());
        patientDTO.setBirthday(LocalDate.parse(entity.getBirthday()));
        List<Location> listLocation=iLocation.findAll();
        modelAndView.addObject("listLocation", listLocation);
        modelAndView.addObject("patient",patientDTO);
        return modelAndView;
    }


    @PostMapping("/user/patient/save_information_patient")
    public String savePatient(ModelMap model,
                             @Valid @ModelAttribute("patient") PatientDTO patientDTO, BindingResult result,
                              RedirectAttributes redirectAttributes){
        editPatientValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            List<Location> listLocation=iLocation.findAll();
            model.addAttribute("listLocation", listLocation);
        return "/user/patient/edit_patient";
    }
        String addressNew=patientDTO.getAddress()+" Quận "+patientDTO.getLocation();
        patientDTO.setAddress(addressNew);
        iPatient.upPatient(patientDTO.getName(), patientDTO.getBirthday(),patientDTO.getAddress(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getGuardianName(),patientDTO.getGuardianPhone(),patientDTO.getId());
        Integer id=patientDTO.getId();
        model.addAttribute("msg","Cập nhật thông tin bệnh nhân thành công!!!");
        redirectAttributes.addFlashAttribute("submitSuccess", true);
        //        Thiết lập thuộc tính "submitSuccess" trong RedirectAttributes
//        Chuyển hướng về trang đăng ký tiêm chủng để hiển thị thông báo thành công
        return "redirect:/vaccination-history/patient";
    }

}
