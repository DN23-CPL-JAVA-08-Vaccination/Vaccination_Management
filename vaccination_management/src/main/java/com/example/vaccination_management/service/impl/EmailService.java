package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    
    /**
       * TLINH
       * set html interface for email
     */
    public void sendHtmlMail(EmailDTO emailDTO, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        Context context = new Context();
        context.setVariables(emailDTO.getProps());
        String html = templateEngine.process(templateName, context);
        helper.setTo(emailDTO.getTo());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(html, true);
            mailSender.send(message);


    }

    /**
       * TLINH
       * send an account activation email with the objects passed to the email form
     */
    @Override
    public Boolean SendEmail(IAccountDetailDTO detailDTO){

        try {
             EmailDTO emailDTO=new EmailDTO();
             emailDTO.setTo(detailDTO.getEmail());
             emailDTO.setSubject("XÁC NHẬP CẤP TÀI KHOẢN TIÊM CHỦNG ĐÀ NẴNG");
            Map<String, Object> props= new HashMap<>();
            props.put("verification_code", detailDTO.getPatientHealthInsurance());
            props.put("password", detailDTO.getPassword());
            props.put("username", detailDTO.getPatientName());
            props.put("phone", detailDTO.getPhone());
            props.put("gender", detailDTO.getGender());
            props.put("address", detailDTO.getAddress());
            props.put("birthday", detailDTO.getBirthday());
            props.put("guardianName", detailDTO.getPatientGuardianName());
            props.put("guardianPhone", detailDTO.getPatientGuardianPhone());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/Email/form_email");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

    
    /**
       * TLINH
       * send an account deactivation email with the objects passed to the email form
     */
    @Override
    public Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO){

        try {
            EmailDTO emailDTO=new EmailDTO();
            emailDTO.setTo(detailDTO.getEmail());
            emailDTO.setSubject("Thông báo hủy  kích hoạt tài khoản tiêm chủng Đà Nẵng");
            Map<String, Object> props= new HashMap<>();
            props.put("verification_code", detailDTO.getPatientHealthInsurance());
            props.put("username", detailDTO.getPatientName());
            props.put("address", detailDTO.getAddress());
            props.put("birthday", detailDTO.getBirthday());
            props.put("gender", detailDTO.getGender());
            props.put("phone", detailDTO.getPhone());
            props.put("guardianName", detailDTO.getPatientGuardianName());
            props.put("guardianPhone", detailDTO.getPatientGuardianPhone());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/Email/form_email_deactivate");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

    /**
       * TLINH
       * Send an email with a link forgot password
     */

    @Override
    public Boolean SendEmailForgotPassword(AccountDTO accountDTO){
        try {
            EmailDTO emailDTO=new EmailDTO();
            emailDTO.setTo(accountDTO.getEmail());
            emailDTO.setSubject("Xác nhận lấy lại mật khẩu");
            Map<String, Object> props= new HashMap<>();
            props.put("username", accountDTO.getUsername());
            props.put("verification_code", accountDTO.getVerificationCode());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "user/Email/form_email_forgot_password");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

    /**
     * TLINH
     * Hide email when checking password reset
     */
    @Override
    public String hideEmail(String email){
        if (email == null || email.isEmpty()) {
            return "";
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return email;
        }

        String username = parts[0];
        String domain = parts[1];

        int len = username.length();
        int numToHide = Math.min(len - 1, 6); // Hiển thị tối đa 6 ký tự đầu tiên (trừ ký tự đầu tiên)

        String hiddenUsername = username.charAt(0) + StringUtils.repeat("*", numToHide);
        String hiddenDomain = StringUtils.repeat("*", domain.length());

        return hiddenUsername + "@" + hiddenDomain;
    }



}
