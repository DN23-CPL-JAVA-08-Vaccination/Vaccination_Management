package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;

import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.IEmployeeService;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IPatientRepository iPatientRepository;

    @Autowired
    private IEmployeeService employeeService;


    /**
     * TLINH
     * set html interface for email
     */
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


    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination) {
        List<Patient> patients = iPatientRepository.findAll();
        String locationName = vaccination.getLocation().getName();
        List<String> matchingPatientNames = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getAddress().toLowerCase().contains(locationName.toLowerCase())) {
                matchingPatientNames.add(patient.getAccount().getEmail());
            }
        }
        return matchingPatientNames;
    }

    @Override
    public Boolean SendEmailTBByLocation(Vaccination vaccination) {
        try {
            EmailDTO emailDTO = new EmailDTO();
            if (vaccination != null) {
                if (!getPatientsWithMatchingLocationName(vaccination).isEmpty()) {
                    for (String account : getPatientsWithMatchingLocationName(vaccination)) {
                        emailDTO.setTo(account);
                    }
                }
            }
            emailDTO.setSubject(vaccination.getDescription());
            Map<String, Object> props = new HashMap<>();
            props.put("description", vaccination.getDescription());

            props.put("vaccinationType", vaccination.getVaccinationType().getName());
            props.put("vaccineName", vaccination.getVaccine().getName());
            props.put("times", vaccination.getTimes());
            props.put("age", vaccination.getVaccine().getAge());

            props.put("startTime", vaccination.getStartTime());
            props.put("endTime", vaccination.getEndTime());
            props.put("duration", vaccination.getDuration());
            props.put("locationDetail", vaccination.getLocation().getLocationDetail());
            props.put("registrationLink", "dangkitiemchung/" + vaccination.getId());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "/admin/vaccination/notification_email");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }

    /**
     * TLINH
     * send an account activation email with the objects passed to the email form
     */

    @Override
    public Boolean SendEmail(IAccountDetailDTO detailDTO) {
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(detailDTO.getEmail());
            emailDTO.setSubject("XÁC NHẬP CẤP TÀI KHOẢN TIÊM CHỦNG ĐÀ NẴNG");
            Map<String, Object> props = new HashMap<>();
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

    @Override
    public Boolean SendEmailCompleted(IVaccinationHistoryDTO ivaccinationHistoryDTO) {

        try {
            EmailDTO emailDTO = new EmailDTO();
            InforEmployeeDTO employeeDTO = employeeService.getInforById(ivaccinationHistoryDTO.getEmployeeId());

            emailDTO.setTo(ivaccinationHistoryDTO.getEmailPatient());
            emailDTO.setSubject("THÔNG BÁO TIÊM CHỦNG THÀNH CÔNG");
            Map<String, Object> props = new HashMap<>();
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

    /**
     * TLINH
     * send an account deactivation email with the objects passed to the email form
     */
    @Override
    public Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO) {

        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(detailDTO.getEmail());
            emailDTO.setSubject("Thông báo hủy  kích hoạt tài khoản tiêm chủng Đà Nẵng");
            Map<String, Object> props = new HashMap<>();
            props.put("verification_code", detailDTO.getPatientHealthInsurance());
            props.put("username", detailDTO.getPatientName());
            props.put("address", detailDTO.getAddress());
            props.put("birthday", detailDTO.getBirthday());
            props.put("gender", detailDTO.getGender());
            props.put("phone", detailDTO.getPhone());
            props.put("guardianName", detailDTO.getPatientGuardianName());
            props.put("guardianPhone", detailDTO.getPatientGuardianPhone());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "admin/Email/form_email_deactivate");
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
    public Boolean SendEmailForgotPassword(AccountDTO accountDTO) {
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(accountDTO.getEmail());
            emailDTO.setSubject("Xác nhận lấy lại mật khẩu");
            Map<String, Object> props = new HashMap<>();
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
    public String hideEmail(String email) {
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
