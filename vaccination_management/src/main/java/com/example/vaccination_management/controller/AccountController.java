package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.validation.RequiresAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    @Autowired
    IEmailService iEmail;

    @Autowired
    private RequiresAccountValidator requiresAccountValidator;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();



    @PostMapping("/insert_account")
    public String requiresAccount(Model model,
                                  @Valid @ModelAttribute("patient") PatientDTO patientDTO,
                                  BindingResult result, HttpServletRequest request){
        requiresAccountValidator.validate(patientDTO, result);
        if (result.hasErrors()){

            return "Patient/requires_account";
        }
//        String password = BCrypt.gensalt(8); // Tạo mật khẩu ngẫu nhiên có độ dài 8 ký tự
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());// Mã hóa mật khẩu

            String password = generatePassword(8);

            patientDTO.setPassword(password);
            patientDTO.setEnableFlag(false);
            iAccount.insertAccount(patientDTO.getName(), patientDTO.getPassword(), patientDTO.getHealthInsurance(), patientDTO.getEmail(), patientDTO.getEnableFlag());

            model.addAttribute("patientDTO", patientDTO);
            return "forward:/patient/insert_patient";
        }
//        return "index";

    @GetMapping("/view_account")
    public String view(Model model, HttpServletRequest request){
        String messageSuccess=request.getParameter("messageSuccess");
        String messageError=request.getParameter("messageError");
        List<Account> list=iAccount.findAll();

        model.addAttribute("messageSuccess",messageSuccess);
        model.addAttribute("messageError",messageError);
        model.addAttribute("accountList", list);
        return "Admin/account";
    }

    @GetMapping("/detail/{id}")
    public String getDetailAccount(@PathVariable("id") Integer id, Model model){
        IAccountDetailDTO iAccountDetail=iAccount.findAccountById(id);


        // Định dạng "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = iAccountDetail.getBirthday().format(formatter);
        // Lưu trữ  vào biến cục bộ
        String patientBirthdayFormatted = formattedDate;

        // Tính tuổi
        LocalDate birthday = iAccountDetail.getBirthday();
        LocalDate now = LocalDate.now();
        Period agePeriod = Period.between(birthday, now);
        int age;
        String ageUnit;
        if (agePeriod.getYears() > 0) {
            age = agePeriod.getYears();
            ageUnit = "year";
        } else if ((agePeriod.getYears()<0)&&(agePeriod.getMonths()>0)){
            age = agePeriod.getMonths();
            ageUnit = "month";
        }else {
            age=agePeriod.getDays();
            ageUnit="days";
        }

        model.addAttribute("account", iAccountDetail);
        model.addAttribute("birthday", patientBirthdayFormatted);
        model.addAttribute("age", age);
        model.addAttribute("ageUnit", ageUnit);
        return "Admin/account_detail";
    }







    @GetMapping("/send_email/{id}/{enableFlag}")
    public ModelAndView sendEmail(@PathVariable("id") Integer id,
                                  @PathVariable("enableFlag") Boolean enableFlag,
                                  ModelMap model){
            IAccountDetailDTO detailDTO=iAccount.findAccountById(id);
            if (!detailDTO.getPatientDeleteFlag()){
                if (detailDTO.getAccountEnableFlag()) {
                    Boolean isEmail = iEmail.SendEmailDeactivate(detailDTO);
                    if (isEmail) {
                        iAccount.updateEnableFlagById(enableFlag, id);
                        model.addAttribute("messageSuccess", "Gửi email hủy kích hoạt tài khoản thành công");
                    } else {
                        model.addAttribute("messageError", "Gửi email hủy kích hoạt tài khoản thất bại");
                    }
                }else {
                    Boolean isEmail = iEmail.SendEmail(detailDTO);
                    if (isEmail) {
                        iAccount.updateEnableFlagById(enableFlag, id);
                        model.addAttribute("messageSuccess", "Gửi email kích hoạt tài khoản thành công");
                    } else {
                        model.addAttribute("messageError", "Gửi email kích hoạt tài khoản thất bại");
                    }
                }
            }else {
                model.addAttribute("messageError","Thông tin tài khoản đã bị vô hiệu hóa, xin vui lòng khôi phục thông tin để tiếp tục");
            }
            return new ModelAndView("redirect:/account/view_account", model);

    }

//Random pass
    private static String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
