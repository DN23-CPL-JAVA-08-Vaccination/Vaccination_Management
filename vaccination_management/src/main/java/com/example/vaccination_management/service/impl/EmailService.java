package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
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

    @Override
    public Boolean SendEmail(IAccountDetailDTO detailDTO){

        try {
             EmailDTO emailDTO=new EmailDTO();
             emailDTO.setTo(detailDTO.getEmail());
             emailDTO.setSubject("XÁC NHẬP CẤP TÀI KHOẢN TIÊM CHỦNG ĐÀ NẴNG");
            Map<String, Object> props= new HashMap<>();
            props.put("verification_code", detailDTO.getAccountVerificationCode());
            props.put("password", detailDTO.getPassword());
            props.put("username", detailDTO.getPatientName());
            props.put("phone", detailDTO.getPhone());
            props.put("address", detailDTO.getAddress());
            props.put("birthday", detailDTO.getBirthday());
            props.put("guardianName", detailDTO.getPatientGuardianName());
            props.put("guardianPhone", detailDTO.getPatientGuardianPhone());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/form_email");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO){

        try {
            EmailDTO emailDTO=new EmailDTO();
            emailDTO.setTo(detailDTO.getEmail());
            emailDTO.setSubject("Thông báo hủy  kích hoạt tài khoản tiêm chủng Đà Nẵng");
            Map<String, Object> props= new HashMap<>();
            props.put("verification_code", detailDTO.getAccountVerificationCode());
            props.put("username", detailDTO.getPatientName());
            props.put("address", detailDTO.getAddress());
            props.put("birthday", detailDTO.getBirthday());
            props.put("gender", detailDTO.getGender());
            props.put("phone", detailDTO.getPhone());
            props.put("guardianName", detailDTO.getPatientGuardianName());
            props.put("guardianPhone", detailDTO.getPatientGuardianPhone());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/form_email_deactivate");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }



}
