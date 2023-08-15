package com.example.vaccination_management.validation;

import com.example.vaccination_management.entity.Vaccine;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VaccineValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Vaccine vaccine = (Vaccine) target;

        String fieldName = "name";
        String fieldCode = "code";
        String fieldPrice = "price";
        String fieldDuration = "duration";
        String fieldDosage = "dosage";
        String fieldAge = "age";

        if (vaccine.getName() == null || vaccine.getName() == "") {
            errors.rejectValue(fieldName, "name.null", "Vui lòng nhập tên Vaccine.");
        } else if (vaccine.getName().trim().length() < 4) {
            errors.rejectValue(fieldName, "name.length", "Tên Vaccine phải lớn hơn 3 ký tự.");
        } else if (vaccine.getName().trim().length() > 20) {
            errors.rejectValue(fieldName, "name.length", "Tên Vaccine phải ít hơn 20 ký tự.");
        }

        if (vaccine.getCode() == null || vaccine.getCode() == "") {
            errors.rejectValue(fieldCode, "code.null", "Vui lòng nhập mã định danh.");
        } else if (vaccine.getCode().trim().length() < 4) {
            errors.rejectValue(fieldCode, "code.length", "Mã định danh phải lớn hơn 3 ký tự.");
        } else if (vaccine.getCode().trim().length() > 20) {
            errors.rejectValue(fieldCode, "code.length", "Mã định danh phải ít hơn 20 ký tự.");
        }

        if (vaccine.getPrice() == null) {
            errors.rejectValue(fieldPrice, "price.null", "Vui lòng nhập đơn giá.");
        } else if (vaccine.getPrice() <= 0) {
            errors.rejectValue(fieldPrice, "price.value", "Đơn giá phải lớn hơn 0 VNĐ");
        }

        if (vaccine.getDuration() == null || vaccine.getDuration() == "") {
            errors.rejectValue(fieldDuration, "pride.null", "Vui lòng nhập thời hạn.");
        }

        if (vaccine.getDosage() == null) {
            errors.rejectValue(fieldDosage, "dosage.null", "Vui lòng nhập liều lượng.");
        } else if (vaccine.getDosage() <= 0) {
            errors.rejectValue(fieldDosage, "dosage.value", "Liều lượng phải lớn hơn 0.");
        }

        if (vaccine.getAge() == null || vaccine.getAge() == "") {
            errors.rejectValue(fieldAge, "age.null", "Vui lòng nhập độ tuổi phù hợp");
        }
    }
}
