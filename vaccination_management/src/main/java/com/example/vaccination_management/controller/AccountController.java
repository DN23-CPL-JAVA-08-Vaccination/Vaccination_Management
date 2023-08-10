package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.IAccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.ILocationService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.validation.CheckPasswordForgot;
import com.example.vaccination_management.validation.ForgotPasswordValidator;
import com.example.vaccination_management.validation.RequiresAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    private CheckPasswordForgot checkPasswordForgot;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    @Autowired
    ILocationService iLocation;


    /**
     * TLINH
     * Insert information to create an account
     */
    @PostMapping("/insert_account")
    public String requiresAccount(Model model,
                                  @Valid @ModelAttribute("patient") PatientDTO patientDTO,
                                  BindingResult result, HttpServletRequest request){
        requiresAccountValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            List<Location> listLocation=iLocation.findAll();
            model.addAttribute("listLocation", listLocation);
            return "Patient/requires_account";
        }
            String password = generatePassword(8);
            patientDTO.setPassword(password);
            patientDTO.setEnableFlag(false);
            iAccount.insertAccount(patientDTO.getHealthInsurance(), patientDTO.getPassword(), patientDTO.getEmail(), patientDTO.getEnableFlag());

            model.addAttribute("patientDTO", patientDTO);
            return "forward:/patient/insert_patient";
        }


    /**
     * TLINH
     * Show  list account, search, pagination
     */
    @GetMapping("/view_account")
    public String view(Model model, HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "") String username ,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "4") int size){
        String msg = request.getParameter("msg");
        Pageable pageable=PageRequest.of(page, size, Sort.by("username").descending());
        List<Account> list=iAccount.getAccountByPage('%'+username+'%', pageable);
        long totalAccount=iAccount.getTotalAccount('%'+username+'%');
        int totalPage= (int) Math.ceil((double) totalAccount/size);


        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", username);
        model.addAttribute("accountList", list);
        model.addAttribute("msg", msg);
        return "Admin/Account/account";
    }


    /**
     * TLINH
     * view account details
     */
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
            ageUnit = "tuổi";
        } else if ((agePeriod.getYears()<0)&&(agePeriod.getMonths()>0)){
            age = agePeriod.getMonths();
            ageUnit = "tháng tuổi";
        }else {
            age=agePeriod.getDays();
            ageUnit="ngày tuổi";
        }

        model.addAttribute("account", iAccountDetail);
        model.addAttribute("birthday", patientBirthdayFormatted);
        model.addAttribute("age", age);
        model.addAttribute("ageUnit", ageUnit);
        return "Admin/Account/account_detail";
    }



    /**
     * TLINH
     * Send email account activation/deactivation
     */
    @GetMapping("/send_email/{id}")
    public ModelAndView sendEmail(@PathVariable("id") Integer id,
                                  ModelMap model){
            IAccountDetailDTO detailDTO=iAccount.findAccountById(id);
            if (!detailDTO.getPatientDeleteFlag()){
                if (detailDTO.getAccountEnableFlag()) {
                    Boolean isEmail = iEmail.SendEmailDeactivate(detailDTO);
                    if (isEmail) {
                        iAccount.updateEnableFlagById(false, id);
                        model.addAttribute("msg", "Gửi email hủy kích hoạt tài khoản thành công");
                    } else {
                        model.addAttribute("msg", "Gửi email hủy kích hoạt tài khoản thất bại");
                    }
                }else {
                    Boolean isEmail = iEmail.SendEmail(detailDTO);
                    if (isEmail) {
                        iAccount.updateEnableFlagById(true, id);
                        model.addAttribute("msg", "Gửi email kích hoạt tài khoản thành công");
                    } else {
                        model.addAttribute("msg", "Gửi email kích hoạt tài khoản thất bại");
                    }
                }
            }else {
                model.addAttribute("msg","Thông tin tài khoản đã bị vô hiệu hóa, xin vui lòng khôi phục thông tin để tiếp tục");
            }
            return new ModelAndView("redirect:/account/view_account", model);

    }

    /**
     * TLINH
     * View form check username
     */
    @GetMapping("/view_check_username")
    public String test(Model model){
       model.addAttribute("accountDTO", new AccountDTO());
        return "Patient/ForgotPassword/check_username";
    }

    /**
     * TLINH
     * view form check email
     */
    @PostMapping("/view_check_email")
    public ModelAndView viewCheckEmail( @Valid @ModelAttribute("accountDTO") AccountDTO accountDTO,
                                        BindingResult result){
        forgotPasswordValidator.validate(accountDTO, result);
        ModelAndView modelAndView=new ModelAndView("Patient/ForgotPassword/check_email");
        if (result.hasErrors()){

            return new ModelAndView("Patient/ForgotPassword/check_username");
        }
        IAccountDTO iAccountDTO=iAccount.findAllByUsername(accountDTO.getUsername());
        accountDTO.setEmail(iAccountDTO.getEmail());
        String hiddenEmail=iEmail.hideEmail(iAccountDTO.getEmail());
        modelAndView.addObject("hiddenEmail",hiddenEmail);
        modelAndView.addObject("accountDTO",accountDTO);
      return modelAndView;
    }


    /**
     * TLINH
     * Send confirmation email forgot password
     */
    @PostMapping("/send_email_forgot_password")
    public ModelAndView sendEmailForgotPassword( @ModelAttribute("accountDTO") AccountDTO accountDTO,
                                                 Model model){
        IAccountDTO iAccountDTO=iAccount.findAllByUsername(accountDTO.getUsername());
        accountDTO.setUsername(iAccountDTO.getUsername());
        String verification_code = generatePassword(24);
        accountDTO.setVerificationCode(verification_code);
        iAccount.updateVerificationCodeByUserName(verification_code,accountDTO.getUsername());
        Boolean isEmail=iEmail.SendEmailForgotPassword(accountDTO);
        if (isEmail) {
            model.addAttribute("messageSuccess", "Gửi yêu cầu thành công \n Vui lòng kiểm tra email của bạn");
        } else {
            model.addAttribute("messageError", "Gửi yêu cầu thất bại/");
        }
        return new ModelAndView("Patient/ForgotPassword/confirm");
    }

    /**
     * TLINH
     * View form forgot password
     */
    @GetMapping("/form_forgot_password/{verification}")
    public String forgotPasswordForm(@PathVariable("verification") String verification, Model model){
        if (iAccount.checkVerificationCode(verification)<1){
            model.addAttribute("messageError","Link truy cập đã hết hạn sử dụng hoặc không tồn tại!!");
            return "Patient/ForgotPassword/confirm";
        }else {
            AccountDTO accountDTO=new AccountDTO();
            accountDTO.setVerificationCode(verification);
            model.addAttribute("account",accountDTO );
            return "Patient/ForgotPassword/form_forgot_password";
        }
    }


    /**
     * TLINH
     * Handling forgot password
     */
    @PostMapping("/forgot_password")
    public ModelAndView forgotPassword
            (@Valid @ModelAttribute("account") AccountDTO account,
                                       BindingResult result){
        checkPasswordForgot.validate(account,result);
        if (result.hasErrors()){
            return new ModelAndView("Patient/ForgotPassword/form_forgot_password");
        }
        ModelAndView modelAndView=new ModelAndView("Patient/ForgotPassword/confirm");
        iAccount.rePasswordByVerificationCode(account.getPassword(),account.getVerificationCode());
        modelAndView.addObject("messageSuccess","Đổi mật khẩu thành công");
        return modelAndView;
    }


    /**
     * TLINH
     * Random Password
     */
    private static String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
