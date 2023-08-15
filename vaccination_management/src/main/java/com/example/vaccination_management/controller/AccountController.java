package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.*;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.service.*;
import com.example.vaccination_management.validation.CheckPasswordForgot;
import com.example.vaccination_management.validation.ForgotPasswordValidator;
import com.example.vaccination_management.validation.RequiresAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("")
public class AccountController {

    @Autowired
    private IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    @Autowired
    IAccountRoleService iAccountRole;

    @Autowired
    IEmailService iEmail;

    @Autowired
    private RequiresAccountValidator requiresAccountValidator;

    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    private CheckPasswordForgot checkPasswordForgot;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    @Autowired
    ILocationService iLocation;


    /**
     * TLINH
     * Insert information to create an account
     */
    @PostMapping("/account/insert_account")
    public String requiresAccount(Model model,
                                  @Valid @ModelAttribute("patient") PatientDTO patientDTO,
                                  BindingResult result, HttpServletRequest request){
        requiresAccountValidator.validate(patientDTO, result);
        if (result.hasErrors()){
            List<Location> listLocation=iLocation.findAll();
            model.addAttribute("listLocation", listLocation);
            return "user/requires_account";
        }

            String password = generatePassword(8);
            patientDTO.setPassword(password);
            patientDTO.setEnableFlag(false);
            iAccount.insertAccount(patientDTO.getHealthInsurance(), patientDTO.getPassword(), patientDTO.getEmail(), patientDTO.getEnableFlag());
            iAccountRole.insertAccountRole(iAccount.findLatestAccountId(),3);
            model.addAttribute("patientDTO", patientDTO);
            return "forward:/patient/insert_patient";
        }


    /**
     * TLINH
     * Show  list account, search, pagination
     */
    @GetMapping("/admin/account/view_account")
    public String view(Model model, HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "") String searchName ,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "4") int size){
        String msg = request.getParameter("msg");

        Pageable pageable = PageRequest.of(page, size, Sort.by("username").descending());

        List<IAccountDTO> list = iAccount.getAllAccountByPage(3, '%' + searchName + '%', pageable);
        long totalAccount = iAccount.getTotalAllAccount(3, '%' + searchName + '%');
        int totalPage = (int) Math.ceil((double) totalAccount / size);
        String actionFlag ="view_account";

        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        model.addAttribute("accountList", list);
        model.addAttribute("actionFlag", actionFlag);
        model.addAttribute("msg", msg);
        return "admin/Account/account";
    }

    @GetMapping("/admin/account/view_account_doctor")
    public String viewAccountDoctor(Model model, HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "") String searchName ,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "4") int size){
        String msg = request.getParameter("msg");
//        Pageable pageable=PageRequest.of(page, size, Sort.by("account.username").descending());
//        List<Account> list=iAccount.getAccountByPage('%'+username+'%', pageable);
//        long totalAccount=iAccount.getTotalAccount('%'+username+'%');
//        int totalPage= (int) Math.ceil((double) totalAccount/size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("username").descending());

        List<IAccountDTO> list = iAccount.getAllAccountByPage(2, '%' + searchName + '%', pageable);
        long totalAccount = iAccount.getTotalAllAccount(2, '%' + searchName + '%');
        int totalPage = (int) Math.ceil((double) totalAccount / size);
        String actionFlag ="view_account_doctor";

        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        model.addAttribute("accountList", list);
        model.addAttribute("actionFlag", actionFlag);
        model.addAttribute("msg", msg);
        return "admin/Account/account";
    }
    @GetMapping("/admin/account/view_account_admin")
    public String viewAccountAdmin(Model model, HttpServletRequest request,
                                    @RequestParam(required = false, defaultValue = "") String searchName ,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "4") int size){
        String msg = request.getParameter("msg");
//        Pageable pageable=PageRequest.of(page, size, Sort.by("account.username").descending());
//        List<Account> list=iAccount.getAccountByPage('%'+username+'%', pageable);
//        long totalAccount=iAccount.getTotalAccount('%'+username+'%');
//        int totalPage= (int) Math.ceil((double) totalAccount/size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("username").descending());

        List<IAccountDTO> list = iAccount.getAllAccountByPage(1, '%' + searchName + '%', pageable);
        long totalAccount = iAccount.getTotalAllAccount(1, '%' + searchName + '%');
        int totalPage = (int) Math.ceil((double) totalAccount / size);
        String actionFlag ="view_account_admin";

        model.addAttribute("totalPages", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        model.addAttribute("accountList", list);
        model.addAttribute("actionFlag", actionFlag);
        model.addAttribute("msg", msg);
        return "admin/Account/account";
    }


    /**
     * TLINH
     * view account details
     */
    @GetMapping("/admin/account/detail/{id}")
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
        return "admin/Account/account_detail";
    }



    /**
     * TLINH
     * Send email account activation/deactivation
     */
    @GetMapping("/admin/account/send_email/{id}")
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
                        iAccount.updatePasswordById(passwordEncoder.encode(detailDTO.getPassword()), id);
                        model.addAttribute("msg", "Gửi email kích hoạt tài khoản thành công");
                    } else {
                        model.addAttribute("msg", "Gửi email kích hoạt tài khoản thất bại");
                    }
                }
            }else {
                model.addAttribute("msg","Thông tin tài khoản đã bị vô hiệu hóa, xin vui lòng khôi phục thông tin để tiếp tục");
            }
            return new ModelAndView("redirect:/admin/account/view_account", model);

    }

    /**
     * TLINH
     * View form check username
     */
    @GetMapping("/account/view_check_username")
    public String test(Model model){
       model.addAttribute("accountDTO", new AccountDTO());
        return "user/ForgotPassword/check_username";
    }

    /**
     * TLINH
     * view form check email
     */
    @PostMapping("/account/view_check_email")
    public ModelAndView viewCheckEmail( @Valid @ModelAttribute("accountDTO") AccountDTO accountDTO,
                                        BindingResult result){
        forgotPasswordValidator.validate(accountDTO, result);
        ModelAndView modelAndView=new ModelAndView("user/ForgotPassword/check_email");
        if (result.hasErrors()){

            return new ModelAndView("user/ForgotPassword/check_username");
        }
        Account account=iAccount.findAllByUsername(accountDTO.getUsername());
        accountDTO.setEmail(account.getEmail());
        String hiddenEmail=iEmail.hideEmail(account.getEmail());
        modelAndView.addObject("hiddenEmail",hiddenEmail);
        modelAndView.addObject("accountDTO",accountDTO);
      return modelAndView;
    }


    /**
     * TLINH
     * Send confirmation email forgot password
     */
    @PostMapping("/account/send_email_forgot_password")
    public ModelAndView sendEmailForgotPassword( @ModelAttribute("accountDTO") AccountDTO accountDTO,
                                                 Model model){
        Account account= iAccount.findAllByUsername(accountDTO.getUsername());
//        IAccountDTO iAccountDTO=iAccount.findAllByUsername(accountDTO.getUsername());
        accountDTO.setUsername(account.getUserName());
        String verification_code = generatePassword(24);
        accountDTO.setVerificationCode(verification_code);
        iAccount.updateVerificationCodeByUserName(verification_code,accountDTO.getUsername());
        Boolean isEmail=iEmail.SendEmailForgotPassword(accountDTO);
        if (isEmail) {
            model.addAttribute("messageSuccess", "Gửi yêu cầu thành công \n Vui lòng kiểm tra email của bạn");
        } else {
            model.addAttribute("messageError", "Gửi yêu cầu thất bại/");
        }
        return new ModelAndView("user/ForgotPassword/confirm");
    }

    /**
     * TLINH
     * View form forgot password
     */
    @GetMapping("/account/form_forgot_password/{verification}")
    public String forgotPasswordForm(@PathVariable("verification") String verification, Model model){
        if (iAccount.checkVerificationCode(verification)<1){
            model.addAttribute("messageError","Link truy cập đã hết hạn sử dụng hoặc không tồn tại!!");
            return "user/ForgotPassword/confirm";
        }else {
            AccountDTO accountDTO=new AccountDTO();
            accountDTO.setVerificationCode(verification);
            model.addAttribute("account",accountDTO );
            return "user/ForgotPassword/form_forgot_password";
        }
    }


    /**
     * TLINH
     * Handling forgot password
     */
    @PostMapping("/account/forgot_password")
    public ModelAndView forgotPassword
            (@Valid @ModelAttribute("account") AccountDTO account,
                                       BindingResult result){
        checkPasswordForgot.validate(account,result);
        if (result.hasErrors()){
            return new ModelAndView("user/ForgotPassword/form_forgot_password");
        }
        ModelAndView modelAndView=new ModelAndView("user/ForgotPassword/confirm");

        iAccount.rePasswordByVerificationCode(passwordEncoder.encode(account.getPassword()),account.getVerificationCode());
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
