package com.example.vaccination_management.validation;


import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * ThangLV
 * validator Employee before update new Employee
 */
@Component
public class EmployeeEditValidator implements Validator {

    @Autowired
    private IEmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeCreateDTO employeeDTO = (EmployeeCreateDTO) target;

        String fieldIdCard = "idCard";
        String fieldPhone = "phone";
        String fieldName = "name";
        String fieldEmail = "email";
        String fieldAddress = "address";

        String regexName = "^[a-zA-Z\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợíìỉĩịđùỳúủũụưứ� �ửữựÀÁýÂÝÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ]*$";
        String regexAddress = "^[a-zA-Z0-9\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợýíìỉĩịđùúủũụưứ� �ửữựÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ ,]*$";


        if (employeeDTO.getName() == null || employeeDTO.getName() == "") {
            errors.rejectValue(fieldName, "name.null", "Vui lòng nhập tên.");
        } else if (employeeDTO.getName().length() < 6) {
            errors.rejectValue(fieldName, "name.length", "Tên phải lớn hơn 5 ký tự.");
        } else if (employeeDTO.getName().length() > 40) {
            errors.rejectValue(fieldName, "name.length", "Tên phải bé hơn 40 ký tự.");
        } else if (!Pattern.compile(regexName).matcher(employeeDTO.getName()).find()) {
            errors.rejectValue(fieldName, "name.pattern", "Tên không được nhập ký tự đặt biệt hoặc số.");
        }

        if (employeeDTO.getBirthday() == null || employeeDTO.getBirthday() == "") {
            errors.rejectValue("birthday", "birthday.null", "Ngày sinh không được để trống.");
        } else {
            SimpleDateFormat formatStringToDate = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            Date dateFormatted = null;
            try {
                dateFormatted = formatStringToDate.parse(employeeDTO.getBirthday());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((now.getYear() - dateFormatted.getYear()) < 18 || (now.getYear() - dateFormatted.getYear()) > 60) {
                errors.rejectValue("birthday", "birthday.format", "Độ tuổi trong khoảng 18 đến 60 tuổi.");
            }
        }

//        if (employeeDTO.isGender()) {
//            errors.rejectValue("gender", "gender.null", "Vui lòng nhập giới tính.");
//        }
        if (employeeDTO.getIdCard() == null || employeeDTO.getIdCard() == "") {
            errors.rejectValue(fieldIdCard, "idCard.null", "Số CMND/CCCD không được để trống.");
        } else if (!Pattern.compile("^(([\\d]{9})|([\\d]{12}))$").matcher(employeeDTO.getIdCard()).find()) {
            errors.rejectValue(fieldIdCard, "idCard.format", "Vui lòng nhập đúng CMND/CCCD.");
        } else if (!employeeDTO.getIdCard().equals(employeeDTO.getCurrentIdCard())) {
            if (employeeService.findByIdCard(employeeDTO.getIdCard()) > 0) {
                errors.rejectValue(fieldIdCard, "idCard.duplicate", "Số CMND/CCCD đã tồn tại.");
            }
        }

        if (employeeDTO.getPhone() == null || employeeDTO.getPhone() == "") {
            errors.rejectValue(fieldPhone, "phone.null", "Số điện thoại không được để trống.");
        } else if (!Pattern.compile("^(\\+?84|0)(3[2-9]|5[689]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$").matcher(employeeDTO.getPhone()).find()) {
            errors.rejectValue(fieldPhone, "phone.format", "Vui lòng nhập số điện thoại đúng định dạng 09xxxxxxx, 03xxxxxxx, 07xxxxxxx, (84) + 90xxxxxxx.");
        } else if (!employeeDTO.getPhone().equals(employeeDTO.getCurrentPhone())) {
            if (employeeService.findByPhone(employeeDTO.getPhone()) > 0) {
                errors.rejectValue(fieldPhone, "phone.duplicate", "Số điện thoại đã tồn tại.");
            }
        }

        if (employeeDTO.getEmail() == null || employeeDTO.getEmail() == "") {
            errors.rejectValue(fieldEmail, "email.null", "Vui lòng nhập email.");
        } else if (!Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matcher(employeeDTO.getEmail()).find()) {
            errors.rejectValue(fieldEmail, "email.format", "Email tuân thủ theo định dạng ex: abc@gmail.com.");
        } else if (!employeeDTO.getEmail().equals(employeeDTO.getCurrentEmail())) {
            if (employeeService.findByEmail(employeeDTO.getEmail()) > 0) {
                errors.rejectValue(fieldEmail, "email.duplicate", "Email đã tồn tại.");
            }
        }

        if (employeeDTO.getAddress() == null || employeeDTO.getAddress() == "") {
            errors.rejectValue(fieldAddress, "address.null", "Vui lòng nhập địa chỉ.");

        } else if (employeeDTO.getAddress().length() > 100) {
            errors.rejectValue(fieldAddress, "address.length", "Địa chỉ phải nhỏ hơn 100 ký tự.");

        } else if (!Pattern.compile(regexAddress).matcher(employeeDTO.getAddress()).find()) {
            errors.rejectValue(fieldAddress, "address.format", "Không được nhập ký tự đặt biệt.");
        }

        if ((Integer) employeeDTO.getPosition().getId() == null || (Integer) employeeDTO.getPosition().getId() == 0) {
            errors.rejectValue("position", "position.null", "Vui lòng chọn chức vụ.");
        }

    }
}
