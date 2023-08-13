package com.example.vaccination_management.validation;


import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class EditPatientValidator implements Validator {
    @Autowired
    private IPatientService iPatient;
    String regex = "^(032|033|034|035|036|037|038|039|070|079|077|076|078|083|084|085|081|082|05[0-9]|056|058|059)\\d{7}$";



    /**
       * TLINH
       * check the data entered in the form edit patient
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return PatientDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        LocalDate currentDate = LocalDate.now();
        PatientDTO patientDTO = (PatientDTO) target;
        if (patientDTO.getName() == null || patientDTO.getName().trim().isEmpty()) {
            errors.rejectValue("name", "NameRequired", "Tên không được để trống!!!");
        } else if (!patientDTO.getName().matches("^[a-zA-Z\\p{L} ]+$")) {
            errors.rejectValue("name", "InvalidCharacters", "Tên khng được chứa các kí tự đặc biệt và số!!!");
        } else if (patientDTO.getName().length() < 6) {
            errors.rejectValue("name", "NameNotLength", "Tên không được ít hơn 6 kí tự!!!");
        }else if (patientDTO.getName().split(" ").length < 2) {
            errors.rejectValue("name", "FullNameRequired", "Vui lòng nhập đầy đủ họ và tên!!!");
        }

        if (patientDTO.getBirthday() == null) {
            errors.rejectValue("birthday", "BirthdayRequired", "Ngày sinh không được để trống!");
        } else if (patientDTO.getBirthday() != null && patientDTO.getBirthday().isAfter(currentDate)){
            errors.rejectValue("birthday", "InvalidDate", "Ngày sinh không đúng định dạng!!");
        }else if (patientDTO.getBirthday().getYear() < 1900) {
            errors.rejectValue("birthday", "InvalidYear", "Năm sinh không được nhỏ hơn 1900.!!");
        }


        if (patientDTO.getAddress()==null||patientDTO.getAddress().trim().isEmpty()){
            errors.rejectValue("address", "AddressRequired", "Địa chỉ là bắt buộc");
        }else {
            if (!patientDTO.getAddress().matches("^[a-zA-Z\\p{L}0-9 ]+$")) {
                errors.rejectValue("address", "InvalidCharacters", "Nhập sai địa chỉ!");
            }else if (patientDTO.getAddress().length()<6){
                errors.rejectValue("address", "Invalid", "Địa chỉ không được ít hơn 6 kí tự!!");
            }
        }
        if (patientDTO.getGender()==null){
            errors.rejectValue("gender", "GenderRequire", "Giới tính là trường bắt buộc");
        }

        if (patientDTO.getPhone()==null||patientDTO.getPhone().trim().isEmpty()){
            errors.rejectValue("phone", "PhoneRequire", "Số điện thoại là bắt buộc");
        } else {
            if (!patientDTO.getPhone().matches(regex)){
                errors.rejectValue("phone", "PhoneFormat", "Số điện thoại sai!!");
            }
        }

        if (patientDTO.getEmail()==null||patientDTO.getEmail().trim().isEmpty()){
            errors.rejectValue("email","EmailRequire", "Email là trường bắt buộc!!");
        }else {
            if (!patientDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                errors.rejectValue("email", "EmailFormat", "Nhập email không đúng!!");
            }
        }
        if (patientDTO.getGuardianName()!=null && !patientDTO.getGuardianName().trim().isEmpty()) {
            if (!patientDTO.getGuardianName().matches("^[a-zA-Z\\p{L} ]+$")) {
                errors.rejectValue("guardianName", "GuardianName", "Nhập đầy đủ họ và tên!!");
            } else if (patientDTO.getGuardianName().length() < 6) {
                errors.rejectValue("guardianName", "Guardian", "Không được ít hơn 6 kí tự!");
            }
        }

        if (patientDTO.getGuardianPhone()!=null && !patientDTO.getGuardianPhone().trim().isEmpty()){
            if (!patientDTO.getGuardianPhone().matches(regex)){
                errors.rejectValue("guardianPhone", "GuardianPhone", "Số điện thoại không đúng!!");
            }
        }
    }

}
