package com.example.vaccination_management.utils;

import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


/**
 * ThangLV
 * validator Employee before insert new Employee
 */
@Component
public class Validation implements Validator {

    /**
     * QuangVT
     * Validate name and phone of guardian
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


    @Override
    public void validate(Object target, Errors errors) {
        VaccinationHistoryDTO vaccinationHistoryDTO = (VaccinationHistoryDTO) target;
        Integer age = vaccinationHistoryDTO.getAgePatient();

        String fieldGuardianPhone = "guardianPhone";
        String fieldGuardianName = "guardianName";
        String fieldPreStauts= "preStatus";

//        String regexName = "^[a-zA-Z0-9 ]*$";
        String regexName = "^[a-zA-Z\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợíìỉĩịđùỳúủũụưứ� �ửữựÀÁýÂÝÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ]*$";


        if (age < 16) {
            if (vaccinationHistoryDTO.getGuardianName() == null || vaccinationHistoryDTO.getGuardianName() == "" ) {
                errors.rejectValue(fieldGuardianName, "guardianName.null", "Vui lòng nhập tên.");
            } else if (vaccinationHistoryDTO.getGuardianName().trim().length() < 6) {
                errors.rejectValue(fieldGuardianName, "guardianName.length", "Tên phải lớn hơn 5 ký tự.");
            } else if (vaccinationHistoryDTO.getGuardianName().trim().length() > 40) {
                errors.rejectValue(fieldGuardianName, "guardianName.length", "Tên phải bé hơn 40 ký tự.");
            } else if (!Pattern.compile(regexName).matcher(vaccinationHistoryDTO.getGuardianName()).find()) {
                errors.rejectValue(fieldGuardianName, "guardianName.pattern", "Tên không được nhập ký tự đặt biệt hoặc số.");
            }
            if (vaccinationHistoryDTO.getGuardianPhone() == null || vaccinationHistoryDTO.getGuardianPhone() == "" ) {
                errors.rejectValue(fieldGuardianPhone, "guardianPhone.null", "Số điện thoại không được để trống.");
            } else if (!Pattern.compile("^(\\+?84|0)(3[2-9]|5[689]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$").matcher(vaccinationHistoryDTO.getGuardianPhone()).find()) {
                errors.rejectValue(fieldGuardianPhone, "guardianPhone.format", "Vui lòng nhập số điện thoại đúng định dạng 09xxxxxxx, 03xxxxxxx, 07xxxxxxx, (84) + 90xxxxxxx.");
            };

        } else if ((vaccinationHistoryDTO.getGuardianName().length() > 0) || (vaccinationHistoryDTO.getGuardianPhone().length() > 0)  ) {
                if (vaccinationHistoryDTO.getGuardianName() == null  || vaccinationHistoryDTO.getGuardianName() == "") {
                    errors.rejectValue(fieldGuardianName, "guardianName.null", "Vui lòng nhập tên.");
                } else if (vaccinationHistoryDTO.getGuardianName().trim().length() < 6) {
                    errors.rejectValue(fieldGuardianName, "guardianName.length", "Tên phải lớn hơn 5 ký tự.");
                } else if (vaccinationHistoryDTO.getGuardianName().trim().length() > 40) {
                    errors.rejectValue(fieldGuardianName, "guardianName.length", "Tên phải bé hơn 40 ký tự.");
                } else if (!Pattern.compile(regexName).matcher(vaccinationHistoryDTO.getGuardianName()).find()) {
                    errors.rejectValue(fieldGuardianName, "guardianName.pattern", "Tên không được nhập ký tự đặt biệt hoặc số.");
                }
                if (vaccinationHistoryDTO.getGuardianPhone() == null || vaccinationHistoryDTO.getGuardianPhone() == "") {
                    errors.rejectValue(fieldGuardianPhone, "guardianPhone.null", "Số điện thoại không được để trống.");
                } else if (!Pattern.compile("^(\\+?84|0)(3[2-9]|5[689]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$").matcher(vaccinationHistoryDTO.getGuardianPhone()).find()) {
                    errors.rejectValue(fieldGuardianPhone, "guardianPhone.format", "Vui lòng nhập số điện thoại đúng định dạng 09xxxxxxx, 03xxxxxxx, 07xxxxxxx, (84) + 90xxxxxxx.");
                }

            }






        if (vaccinationHistoryDTO.getPreStatus() == null) {
            errors.rejectValue(fieldPreStauts, "preStatus.null", "Vui lòng nhập trạng thái của bệnh nhân.");
        } else if (vaccinationHistoryDTO.getPreStatus() == "") {
            errors.rejectValue(fieldPreStauts, "preStatus.null", "Vui lòng nhập trạng thái của bệnh nhân.");
        }  else if (vaccinationHistoryDTO.getPreStatus().trim().length() < 6) {
            errors.rejectValue(fieldPreStauts, "preStatus.length", "Trạng thái của bệnh nhân phải lớn hơn 5 ký tự.");
        } else {
            if (vaccinationHistoryDTO.getPreStatus().trim().length() > 100) {
                errors.rejectValue(fieldPreStauts, "preStatus.length", "Trạng thái bệnh nhân phải nhỏ hơn 100 ký tự.");

            }
        }


    }
}
