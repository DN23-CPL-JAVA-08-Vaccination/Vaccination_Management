package com.example.vaccination_management.validation;


import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;
import java.time.Period;


@Component
public class RequiresAccountValidator implements Validator {
    @Autowired
    private IPatientService iPatient;

    String regex = "^(032|033|034|035|036|037|038|039|070|079|077|076|078|083|084|085|081|082|05[0-9]|056|058|059)\\d{7}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return PatientDTO.class.equals(clazz);
    }

    /**
       * TLINH
       * check the data entered in the form requires account 
     */
    @Override
    public void validate(Object target, Errors errors) {


        LocalDate currentDate = LocalDate.now();
        PatientDTO patientDTO = (PatientDTO) target;
        if (patientDTO.getName() == null || patientDTO.getName().trim().isEmpty()) {
            errors.rejectValue("name", "NameRequired", "Không được để trống!!!");
        } else if (!patientDTO.getName().matches("^[a-zA-Z\\p{L} ]+$")) {
            errors.rejectValue("name", "InvalidCharacters", "Tên không được chứa các kí tự đặc biệt và số!!!");
        } else if (patientDTO.getName().length() < 6) {
            errors.rejectValue("name", "NameNotLength", "Tên không được ít hơn 6 kí tự!!!");
        }else if (patientDTO.getName().split(" ").length < 2) {
            errors.rejectValue("name", "FullNameRequired", "Vui lòng nhập đầy đủ họ và tên!!!");
        }

        if (patientDTO.getBirthday() == null) {
            errors.rejectValue("birthday", "BirthdayRequired", "Không được để trống!");
        } else if (patientDTO.getBirthday() != null && patientDTO.getBirthday().isAfter(currentDate)){
            errors.rejectValue("birthday", "InvalidDate", "Ngày sinh không đúng!!");
        }else if (patientDTO.getBirthday().getYear() < 1900) {
            errors.rejectValue("birthday", "InvalidYear", "Năm sinh không nhỏ hơn 1900.!!");
        }

        if (patientDTO.getHealthInsurance()==null||patientDTO.getHealthInsurance().trim().isEmpty()){
            errors.rejectValue("healthInsurance", "HealthInsuranceRequired", "Không được để trống!!");
        }else {
            if (!patientDTO.getHealthInsurance().matches("^[A-Z]{2}[0-5][0-9]{2}[0-9]{10}$")){
                errors.rejectValue("healthInsurance", "HealthInsuranceFormat", "BHYT không đúng!!");
            }else if (iPatient.finByHealthInsurance(patientDTO.getHealthInsurance())>0){
                errors.rejectValue("healthInsurance", "already", "BHYT đã tồn tại");
            }
        }

        if (patientDTO.getLocation() == null || patientDTO.getLocation().trim().isEmpty()){
            errors.rejectValue("location", "required","Không được để trống !!!");
        }

        if (patientDTO.getAddress()==null||patientDTO.getAddress().trim().isEmpty()){
            errors.rejectValue("address", "AddressRequired", "Không được để trống!!");
        }else {
            if (!patientDTO.getAddress().matches("^[a-zA-Z\\p{L}0-9]+$")) {
                errors.rejectValue("address", "InvalidCharacters", "Địa chỉ không đúng!");
            }else if (patientDTO.getAddress().length()<6){
                errors.rejectValue("address", "Invalid", "Địa chỉ không ít hơn 6 kí tự!!");
            }
        }
        if (patientDTO.getGender()==null){
            errors.rejectValue("gender", "GenderRequire", "Không được để trống!!");
        }

        if (patientDTO.getPhone()==null||patientDTO.getPhone().trim().isEmpty()){
            errors.rejectValue("phone", "PhoneRequire", "Không được để trống");
        } else {
            if (!patientDTO.getPhone().matches(regex)){
                errors.rejectValue("phone", "PhoneFormat", "Số điện thoại không đúng!!");
            }
        }

        if (patientDTO.getEmail()==null||patientDTO.getEmail().trim().isEmpty()){
            errors.rejectValue("email","EmailRequire", "Email không được để trống!!");
        }else {
            if (!patientDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                errors.rejectValue("email", "EmailFormat", "Email không đúng!!");
            }
        }
        if (patientDTO.getBirthday() != null){
            Period agePeriod = Period.between(patientDTO.getBirthday(),currentDate);
            if (agePeriod.getYears()<16) {
                if (patientDTO.getGuardianName() == null || patientDTO.getGuardianName().trim().isEmpty()){
                    errors.rejectValue("guardianName", "GuardianNameb", "Không được để trống!!");
                }else {
                    if (!patientDTO.getGuardianName().matches("^[a-zA-Z\\p{L} ]+$")) {
                        errors.rejectValue("guardianName", "GuardianName", "Nhập đầy đủ họ tên!!");
                    } else if (patientDTO.getGuardianName().length() < 6) {
                        errors.rejectValue("guardianName", "Guardian", "Tên không ít hơn 6 kí tự!");
                    }
                }
                if (patientDTO.getGuardianPhone() == null || patientDTO.getGuardianPhone().trim().isEmpty()) {
                    errors.rejectValue("guardianPhone", "GuardianPhoneb", "Không được để trống!!");
                }else{
                    if (!patientDTO.getGuardianPhone().matches(regex)) {
                        errors.rejectValue("guardianPhone", "GuardianPhone", "Số điện thoại không đúng!!");
                    }
                }
            }
        }
    }

}
