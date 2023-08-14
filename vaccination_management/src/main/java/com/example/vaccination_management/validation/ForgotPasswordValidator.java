package com.example.vaccination_management.validation;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ForgotPasswordValidator implements Validator {

    @Autowired
    private IAccountService iAccountService;

    /**
     * TLINH
     * check the data entered in the form forgot pass
     */

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;

        if (accountDTO.getUsername() == null || accountDTO.getUsername().isEmpty()) {
            errors.rejectValue("username", "Required", "Mã đăng nhập không được để trống!!!");
        } else {
          if (iAccountService.finByUserName(accountDTO.getUsername()) < 1) {
                errors.rejectValue("username", "UserNameExits", "Mã đăng nhập không tồn tại!!!");

            }
        }
    }

}
