package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.validation.EditPatientValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    @Autowired
    EditPatientValidator editPatientValidator;

    @GetMapping("/requires_account")
    public String viewFormRequires(ModelMap model, HttpServletRequest request){
        String message=request.getParameter("message");
        model.addAttribute("message",message);
        model.addAttribute("patient", new PatientDTO());
        return "Patient/requires_account";
    }

    @PostMapping("/insert_patient")
    public String insertPatient(@ModelAttribute("patientDTO") PatientDTO patientDTO,
                                RedirectAttributes redirectAttributes){
        patientDTO.setDeleteFlag(false);
        patientDTO.setAccount(iAccount.findLatestAccountId());
      iPatient.insertPatient(patientDTO.getName(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getAddress(),patientDTO.getBirthday(),patientDTO.getHealthInsurance(),patientDTO.getGuardianName(),
              patientDTO.getGuardianPhone(),patientDTO.getDeleteFlag(),patientDTO.getAccount());
        redirectAttributes.addAttribute("message", "Gửi yêu cầu tài khoản tiêm chủng thành công!!");
      return "redirect:/patient/requires_account";
    }



    @GetMapping("/detail/{id}")
    public String patientDetail(Model model,
                                @PathVariable("id") Integer id){

//        Patient patient=iPatient.getById(id);
        Optional<Patient> op=iPatient.findById(id);
        PatientDTO patientDTO=new PatientDTO();
        Patient patient=op.get();
        BeanUtils.copyProperties(patient,patientDTO);
        patientDTO.setPhone(patient.getPhoneNumber());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Lấy giá trị Date từ đối tượng Patient (đối tượng java.util.Date)
        Date birthdayDate = Date.valueOf(patient.getBirthday());
        // Định dạng Date thành chuỗi
        String formattedDate = formatter.format(birthdayDate);
        patientDTO.setFormBirthday(formattedDate);

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
        return "Admin/patient_detail";
    }

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
        return new ModelAndView("Admin/patient_edit",model);
//        để đơn giản hóa việc truyền dữ liệu từ Controller đến view và  dễ dàng hiển thị các trang HTML động với dữ liệu từ phía Server.
    }


    @GetMapping("/save")
    public ModelAndView save(ModelMap model,
                             @Valid @ModelAttribute("patient") PatientDTO patientDTO, BindingResult result){
        editPatientValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            return new ModelAndView("Admin/patient_edit");
        }
        iPatient.upPatient(patientDTO.getName(), patientDTO.getBirthday(),patientDTO.getAddress(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getGuardianName(),patientDTO.getGuardianPhone(),patientDTO.getId());
        model.addAttribute("messageSuccess","Chỉnh sửa thông tin bệnh nhân thành công!!!");
        return new ModelAndView("redirect:/patient/view_patient", model);
    }
    @GetMapping("/view_patient_disable")
    public String viewPatientDisable(Model model){
        List<Patient> patientList=iPatient.fillAllByAccountIDisNull();
        model.addAttribute("patientList",patientList);
        return "Admin/patient";
    }

    @GetMapping("/view_patient")
    public String view(Model model, HttpServletRequest request){
        String messageSuccess=request.getParameter("messageSuccess");
        String messageError=request.getParameter("messageError");
        List<Patient> patientList=iPatient.findAllByDeleteFlag(false);
        model.addAttribute("messageSuccess",messageSuccess);
        model.addAttribute("messageError",messageError);
        model.addAttribute("patientList",patientList);
        return "Admin/patient";
    }

    @GetMapping("/view_patientDelete")
    public String viewDelete(Model model){
        List<Patient> patientList=iPatient.findAllByDeleteFlag(true);
        model.addAttribute("patientList",patientList);
        return "Admin/patient";
    }

    @GetMapping("/delete_flag/{id}/{deleteFlag}")
    public ModelAndView deleteFlag(@PathVariable("id") Integer id,
                             @PathVariable("deleteFlag") Boolean deleteFlag){
        Optional<Patient> patient=iPatient.findById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/patient/view_patient");
        iPatient.updateDeleteFlagById(deleteFlag,id);
        if (deleteFlag){
            iAccount.updateEnableFlagById(false,patient.get().getAccount().getId());
            modelAndView.addObject("messageSuccess", "Xóa thông tin bệnh nhân thành công!!");
        }else {
            modelAndView.addObject("messageSuccess", "Khôi phục thông tin bệnh nhân thành công!!");
        }
        return modelAndView;
    }

//    @GetMapping("/delete_account/{id}")
//    public ModelAndView deleteAccount(ModelMap model,
//                                      @PathVariable("id") Integer id){
//        iAccount.deleteById(id);
//        model.addAttribute("messageSuccess","Xóa tài khoản thành công!!");
//        return new ModelAndView("redirect:/account/view_account",model);
//    }

    @GetMapping("/storage/{id}")
    public ModelAndView storage(@PathVariable("id") Integer id){
        ModelAndView modelAndView=new ModelAndView("redirect:/patient/view_patient");
        Optional<Patient> patient=iPatient.findById(id);
        iAccount.deleteById(patient.get().getAccount().getId());
        LocalDate currentDate=LocalDate.now();
        modelAndView.addObject("messageSuccess","Lưu trữ thông tin bệnh nhân thành công");
        modelAndView.addObject("currentDate",currentDate);
        return modelAndView;
    }







}
