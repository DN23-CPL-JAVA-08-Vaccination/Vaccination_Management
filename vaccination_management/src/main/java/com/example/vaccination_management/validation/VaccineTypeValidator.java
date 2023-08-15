package com.example.vaccination_management.validation;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class VaccineTypeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        VaccineType vaccineType = (VaccineType) target;

        String fieldName = "name";

        String regaxName = ".*\\d.*";

        if (vaccineType.getName() == null || vaccineType.getName() == "") {
            errors.rejectValue(fieldName, "name.null", "Vui lòng nhập tên loại vaccine.");
        } else if (vaccineType.getName().trim().length() < 5) {
            errors.rejectValue(fieldName, "name.length", "Tên loại vaccine tối thiểu 5 kí tự.");
        } else if (vaccineType.getName().trim().length() > 50) {
            errors.rejectValue(fieldName, "name.length", "Tên loại vaccine tối đa 50 kí tự.");
        } else if (Pattern.compile(regaxName).matcher(vaccineType.getName()).find()) {
            errors.rejectValue(fieldName, "name.pattern", "Tên không được nhập số.");
        }
    }
}
