package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.IEmployeeService;
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
    @Autowired
    private IEmployeeService employeeService;
    @Override
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
    public Boolean SendEmailCompleted(IVaccinationHistoryDTO ivaccinationHistoryDTO){

        try {
            EmailDTO emailDTO = new EmailDTO();
            InforEmployeeDTO employeeDTO = employeeService.getInforById(ivaccinationHistoryDTO.getEmployeeId());

            emailDTO.setTo(ivaccinationHistoryDTO.getEmailPatient());
            emailDTO.setSubject("THÔNG BÁO TIÊM CHỦNG THÀNH CÔNG");
            Map<String, Object> props= new HashMap<>();
            props.put("patientName", ivaccinationHistoryDTO.getPatientName());
            props.put("birthday", ivaccinationHistoryDTO.getPatientBirth());
            props.put("vaccination", ivaccinationHistoryDTO.getVaccinationDesc());
            props.put("vaccine", ivaccinationHistoryDTO.getVaccineName());
            props.put("dosage", ivaccinationHistoryDTO.getDosage());
            props.put("duration", ivaccinationHistoryDTO.getDuration());
            props.put("times", ivaccinationHistoryDTO.getVaccinationTimes());
            props.put("regis", ivaccinationHistoryDTO.getRegisTimeFormatted());
            props.put("employee", employeeDTO.getName());
            props.put("employeePhone", employeeDTO.getPhone());
            props.put("guardianName", ivaccinationHistoryDTO.getGuardianName());
            props.put("guardianPhone", ivaccinationHistoryDTO.getGuardianPhone());
            props.put("preStatus", ivaccinationHistoryDTO.getPreStatus());
            props.put("status", ivaccinationHistoryDTO.getStatus());
            props.put("lastTime", ivaccinationHistoryDTO.getLastTimeFormatted());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/Email/completed_form");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }
}
