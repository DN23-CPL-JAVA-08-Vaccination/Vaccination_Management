package com.example.vaccination_management.validator;

import com.example.vaccination_management.dto.CreateVaccinationHistoryDTO;
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static com.example.vaccination_management.utils.DateUtils.calculateAge;


@Component
public class VaccinationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateVaccinationHistoryDTO vaccinationHistoryDTO = (CreateVaccinationHistoryDTO) target;

        int age = DateUtils.calculateAge(vaccinationHistoryDTO.getPatient().getBirthday());

        String fieldGuardianName = "guardianName";
        String fieldGuardianPhone = "guardianPhone";
//        String fieldEndTime = "endTime";

        String regexName = "^[a-zA-Z\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợíìỉĩịđùỳúủũụưứ� �ửữựÀÁýÂÝÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ]*$";

        LocalDateTime startTime = LocalDateTime.parse(vaccinationHistoryDTO.getVaccination().getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(vaccinationHistoryDTO.getVaccination().getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime selectedDateTime = LocalDateTime.parse(vaccinationHistoryDTO.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        if (selectedDateTime.isBefore(startTime) || selectedDateTime.isAfter(endTime)){
            errors.rejectValue("endTime", "timeError", "Không hợp lý.");
        }

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
                errors.rejectValue(fieldGuardianName, "guardianName.null", "Tên không được để trống.");
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


    }
}