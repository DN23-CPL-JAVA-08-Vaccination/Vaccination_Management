package com.example.vaccination_management.validation;

import com.example.vaccination_management.dto.ChangeAccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * ThangLV
 * validator password before change password
 */
@Component
public class ChangePasswordValidator implements Validator {

    private final String fieldCurrentPassword = "currentPassword";
    private final String fieldNewPassword = "newPassword";
    private final String fieldConfirmPassword = "confirmPassword";
    private final String patternNewPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[#$@!%&*?-_])[A-Za-z#$@!%&*?-_]{6,15}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangeAccountDTO changeAccountDTO = (ChangeAccountDTO) target;
        if (changeAccountDTO.getCurrentPassword().equals("")) {
            errors.rejectValue(fieldCurrentPassword, "currentPassword.null", "Mật khẩu hiện tại không được để trống.");
        } else if (changeAccountDTO.getCurrentPassword().length() < 6 || changeAccountDTO.getCurrentPassword().length() > 15) {
            errors.rejectValue(fieldCurrentPassword, "currentPassword.length", "Mật khẩu hiện tại từ 6 đến 15 ký tự.");
        }
        if (changeAccountDTO.getNewPassword().equals("")) {
            errors.rejectValue(fieldNewPassword, "newPassword.null", "Mật khẩu mới không được để trống.");
        } else if (!Pattern.compile(patternNewPassword).matcher(changeAccountDTO.getNewPassword()).find()) {
            errors.rejectValue(fieldNewPassword, "newPassword.pattern", "Mật khẩu mới từ 6 -15 ký tự, gồm chữ thường, chữ hoa, ký tự đặc biệt.");
        } else if (changeAccountDTO.getCurrentPassword().equals(changeAccountDTO.getNewPassword())) {
            errors.rejectValue(fieldNewPassword, "newPassword.checkMatches", "Mật khẩu mới không được trùng mật khẩu hiện tại.");
        }
        if (changeAccountDTO.getConfirmPassword().equals("")) {
            errors.rejectValue(fieldConfirmPassword, "confirmPassword.null", "Vui lòng xác nhận lại mật khẩu.");
        } else if (!changeAccountDTO.getNewPassword().equals(changeAccountDTO.getConfirmPassword())) {
            errors.rejectValue(fieldConfirmPassword, "confirmPassword.null", "Mật khẩu không khớp.");
        }
    }
}