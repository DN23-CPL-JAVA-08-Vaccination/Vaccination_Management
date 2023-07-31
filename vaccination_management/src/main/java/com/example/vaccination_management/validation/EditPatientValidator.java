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
            errors.rejectValue("birthday", "BirthdayRequired", "Birthday is required!");
        } else if (patientDTO.getBirthday() != null && patientDTO.getBirthday().isAfter(currentDate)){
            errors.rejectValue("birthday", "InvalidDate", "Birthday cannot be in the future.!!");
        }else if (patientDTO.getBirthday().getYear() < 1900) {
            errors.rejectValue("birthday", "InvalidYear", "Birthday cannot be earlier than 1900.!!");
        }


        if (patientDTO.getAddress()==null||patientDTO.getAddress().trim().isEmpty()){
            errors.rejectValue("address", "AddressRequired", "Address is Required");
        }else {
            if (!patientDTO.getAddress().matches("^[a-zA-Z\\p{L}0-9 ]+$")) {
                errors.rejectValue("address", "InvalidCharacters", "Address must not contain special characters!");
            }else if (patientDTO.getAddress().length()<6){
                errors.rejectValue("address", "Invalid", "Invalid address!!");
            }
        }
        if (patientDTO.getGender()==null){
            errors.rejectValue("gender", "GenderRequire", "Gender is Required");
        }

        if (patientDTO.getPhone()==null||patientDTO.getPhone().trim().isEmpty()){
            errors.rejectValue("phone", "PhoneRequire", "Phone is Require");
        } else {
            if (!patientDTO.getPhone().matches("^[0-9]{10}$")){
                errors.rejectValue("phone", "PhoneFormat", "Incorrect phone number!!");
            }
        }

        if (patientDTO.getEmail()==null||patientDTO.getEmail().trim().isEmpty()){
            errors.rejectValue("email","EmailRequire", "Email is Require!!");
        }else {
            if (!patientDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                errors.rejectValue("email", "EmailFormat", "Email is not correct!!");
            }
        }
        if (patientDTO.getGuardianName()!=null && !patientDTO.getGuardianName().trim().isEmpty()) {
            if (!patientDTO.getGuardianName().matches("^[a-zA-Z\\p{L} ]+$")) {
                errors.rejectValue("guardianName", "GuardianName", "Name cannot contain characters and numbers!!");
            } else if (patientDTO.getGuardianName().length() < 6) {
                errors.rejectValue("guardianName", "Guardian", "Name must be more than 6 characters!");
            }
        }

        if (patientDTO.getGuardianPhone()!=null && !patientDTO.getGuardianPhone().trim().isEmpty()){
            if (!patientDTO.getGuardianPhone().matches("^[0-9]{10}$")){
                errors.rejectValue("guardianPhone", "GuardianPhone", "Incorrect phone number!!");
            }
        }
    }

}
