package com.example.vaccination_management.validation;

import com.example.vaccination_management.entity.Vaccine;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

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

        String regexAge = "[0-9-]+";

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
            errors.rejectValue(fieldAge, "age.null", "Vui lòng nhập độ tuổi phù hợp ở dạng n-m với n và m là độ tuổi");
        } else if (vaccine.getAge().trim().length() < 3) {
            errors.rejectValue(fieldAge, "age.length", "Nhập ít nhất 3 kí tự.");
        } else if (vaccine.getAge().trim().length() > 20) {
            errors.rejectValue(fieldAge, "age.length", "Nhập tối đa 20 kí tự.");
        } else if (!Pattern.compile(regexAge).matcher(vaccine.getAge().trim()).find()) {
            errors.rejectValue(fieldAge, "age.pattern", "Chỉ được nhập số và kí tự '-'.");
        } else {
            String[] split = vaccine.getAge().trim().split("-");

            if (split.length != 2) {
                errors.rejectValue(fieldAge, "age.split", "Nhập dạng độ tuổi không hợp lệ, độ tuổi ở dạng n-m với n và m là độ tuổi.");
            } else {
                int minAge = Integer.parseInt(split[0]);
                int maxAge = Integer.parseInt(split[1]);

                if (minAge >= maxAge) {
                    errors.rejectValue(fieldAge, "age.needAge", "Độ tuổi không hợp lệ dạng đúng phải là n < m.");
                } else if (minAge < 1 || maxAge > 80) {
                    errors.rejectValue(fieldAge, "age.needAge", "Độ tuổi nằm trong khoảng từ 1 đến 80 tuổi.");
                }
            }
        }
    }
}
