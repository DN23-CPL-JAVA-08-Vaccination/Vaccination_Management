package com.example.vaccination_management.validation;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.service.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VaccinationEventValidator implements Validator {

    @Autowired
    private IVaccinationService vaccinationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Vaccination.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Vaccination vaccinationCheck = (Vaccination) target;

        if (vaccinationCheck.getDuration() == null || vaccinationCheck.getDuration().trim().isEmpty()) {
            errors.rejectValue("duration", "duration.require", "Không được để trống!!!");
        } else if (vaccinationCheck.getDuration().length() > 50) {
            errors.rejectValue("duration", "maxlength", "Không được vượt quá 50 kí tự!!!");
        } else if (containsSpecialCharacters(vaccinationCheck.getDuration())) {
            errors.rejectValue("duration", "duration.regex", "Không chứa kí tự đặc biệt!!!");
        }
        if (vaccinationCheck.getDescription() == null || vaccinationCheck.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "description.require", "Không được để trống!!!");
        } else if (containsSpecialCharacters(vaccinationCheck.getDescription())) {
            errors.rejectValue("description", "description.regex", "Không chứa kí tự đặc biệt!!!");
        }else if(vaccinationCheck.getDescription().length()>150){
            errors.rejectValue("description", "maxlength", "Không được vượt quá 150 kí tự!!!");

        }
        if (vaccinationCheck.getStartTime() == null || vaccinationCheck.getStartTime().isEmpty()) {
            errors.rejectValue("startTime", "startTime.require", "Không được để trống!!!");
        }
        if (vaccinationCheck.getEndTime() == null || vaccinationCheck.getEndTime().isEmpty()) {
            errors.rejectValue("endTime", "endTime.require", "Không được để trống!!!");
        }

        if (vaccinationCheck.getTimes() == 0 || vaccinationCheck.getTimes() < 0 || vaccinationCheck.getTimes() >10000){
            errors.rejectValue("times","require", "Không hợp lệ !!!");
        }
    }

    private boolean containsSpecialCharacters(String input) {
        String specialCharacters = "!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?";
        for (char c : specialCharacters.toCharArray()) {
            if (input.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}
