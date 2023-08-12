package com.example.vaccination_management.validation;

import com.example.vaccination_management.dto.AccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CheckPasswordForgot implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        /**
         * TLINH
         * check the password entered in the form forgot pass
         */
        if (accountDTO.getPassword() == null || accountDTO.getPassword().trim().isEmpty()){
            errors.rejectValue("password","passRequire","Vui lòng không để trống!!");
        }else {
                String password = accountDTO.getPassword().trim();
                if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
                    errors.rejectValue("password", "passInvalid", "Mật khẩu không hợp lệ!! Yêu cầu: nhiều hơn 8 kí tự gồm số và chữ, chứa ít nhất 1 chữ in hoa");
                } else {
                    String repassword = accountDTO.getRePassword().trim();
                    if (!repassword.equals(password)) {
                        errors.rejectValue("rePassword", "repassMismatch", "Mật khẩu nhập lại không giống với mật khẩu ban đầu");
                    }
                }

        }
    }
}
