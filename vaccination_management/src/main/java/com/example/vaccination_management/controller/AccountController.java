package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    @PostMapping("/insert_account")
    public String requiresAccount(Model model,
                                  @Valid @ModelAttribute("patient") PatientDTO patientDTO,
                                  BindingResult result, HttpServletRequest request){
        if (result.hasErrors()){
            return "forward:/patient/requires_account";
        }
//        String password = BCrypt.gensalt(8); // Tạo mật khẩu ngẫu nhiên có độ dài 8 ký tự
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());// Mã hóa mật khẩu

        String password=generatePassword(8);

        patientDTO.setPassword(password);
        patientDTO.setEnableFlag(false);
        iAccount.insertAccount(patientDTO.getName(), patientDTO.getPassword(), patientDTO.getHealthInsurance(), patientDTO.getEmail(), patientDTO.getEnableFlag());

        model.addAttribute("patientDTO", patientDTO);
        return "forward:/patient/insert_patient";
//        return "index";
    }

    @GetMapping("/view_account")
    public String view(Model model){
        List<Account> list=iAccount.findAll();
        model.addAttribute("accountList", list);
        return "Admin/Account";
    }

    @GetMapping("/detail/{id}")
    public String getDetailAccount(@PathVariable("id") Integer id, Model model){
        IAccountDetailDTO iAccountDetail=iAccount.findAccountById(id);
        // Định dạng ngày sinh theo định dạng "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = iAccountDetail.getBirthday().format(formatter);

        // Lưu trữ giá trị định dạng mới vào biến cục bộ
        String patientBirthdayFormatted = formattedDate;

        model.addAttribute("account", iAccountDetail);
        model.addAttribute("birthday", patientBirthdayFormatted);
        return "Admin/account_detail";
    }



    @GetMapping("/test")
    public String test(Model model){
        return "Admin/account_detail";
    }




    private static String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
