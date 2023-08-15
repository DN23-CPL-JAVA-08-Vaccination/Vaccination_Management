package com.example.vaccination_management.validation;

import com.example.vaccination_management.entity.Inventory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class InventoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Inventory inventory = (Inventory) target;

        String fieldQuantity = "quantity";
        String fieldSupplier = "supplier";
        String fieldProduction = "productionDate";
        String fieldExpiration = "expirationDate";
        String fieldAddress = "address";
        String fieldName = "inventoryName";

        String regexName = "^[a-zA-Z0-9\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợýíìỉĩịđùúủũụưứ� �ửữựÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ ,]*$";
        String regexAddress = "^[a-zA-Z0-9\'-\'\\sáàảãạăâắằấầặẵẫậéèẻ ẽẹếềểễệóêòỏõọôốồổỗộ ơớờởỡợýíìỉĩịđùúủũụưứ� �ửữựÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠ ƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼ� ��ỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞ ỠỢỤỨỪỬỮỰỲỴÝỶỸửữựỵ ỷỹ ,]*$";

        if (inventory.getQuantity() == 0) {
            errors.rejectValue(fieldQuantity, "quantity.null", "Vui lòng nhập số lượng vaccine.");
        } else if (inventory.getQuantity() < 0) {
            errors.rejectValue(fieldQuantity, "quantity.value", "Số lượng phải lớn hơn 0");
        }

        if (inventory.getSupplier() == null || inventory.getSupplier() == "") {
            errors.rejectValue(fieldSupplier, "supplier.null", "Vui lòng nhập nhà cung cấp lô hàng này.");
        } else if (inventory.getSupplier().trim().length() < 5) {
            errors.rejectValue(fieldSupplier, "supplier.length", "Nhà cung cấp phải dài hơn 5 kí tự");
        } else if (inventory.getSupplier().trim().length() > 50) {
            errors.rejectValue(fieldSupplier, "supplier.length", "Nhà cung cấp phải ít hơn 50 kí tự");
        }

        if (inventory.getProductionDate() == null || inventory.getProductionDate() == "") {
            errors.rejectValue(fieldProduction, "production.null", "Ngày sản xuất không được để trống.");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            Date dateFormatter = null;

            try {
                dateFormatter = formatter.parse(inventory.getProductionDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dateFormatter.after(now) || dateFormatter.equals(now)) {
                errors.rejectValue(fieldProduction, "production.value", "Ngày sản xuất phải trước ngày hiện tại.");
            }
        }
        
        if (inventory.getExpirationDate() == null || inventory.getExpirationDate() == "") {
            errors.rejectValue(fieldExpiration, "expiration.null", "Hạn sử dụng không được để trống.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate production = LocalDate.parse(inventory.getProductionDate(), formatter);
            LocalDate expiration = LocalDate.parse(inventory.getExpirationDate(), formatter);

            if (expiration.equals(production)) {
                errors.rejectValue(fieldExpiration, "expiration.value", "Hạn sử dụng không được trùng với ngày sản xuất.");
            } else if (Period.between(production, expiration).getMonths() < 3 || Period.between(production, expiration).getMonths() > 24) {
                errors.rejectValue(fieldExpiration, "expiration.value", "Hạn sử dụng tối thiểu là 3 tháng và tối đa là 24 tháng.");
            }
        }

        if (inventory.getInventoryName() == null || inventory.getInventoryName() == "") {
            errors.rejectValue(fieldName, "name.null", "Vui lòng nhập tên lô hàng.");
        } else if (inventory.getInventoryName().trim().length() < 5) {
            errors.rejectValue(fieldName, "name.length", "Tên lô hàng ít nhất 5 kí tự.");
        } else if (inventory.getInventoryName().trim().length() > 20) {
            errors.rejectValue(fieldName, "name.length", "Tên lô hàng tối đa 20 kí tự.");
        } else if (!Pattern.compile(regexName).matcher(inventory.getInventoryName().trim()).find()) {
            errors.rejectValue(fieldName, "name.pattern", "Tên không được nhập ký tự đặt biệt.");
        }

        if (inventory.getAddress() == null || inventory.getAddress() == "") {
            errors.rejectValue(fieldAddress, "address.null", "Vui lòng nhập địa chỉ công ty.");
        } else if (inventory.getAddress().trim().length() < 6) {
            errors.rejectValue(fieldAddress, "address.length", "Địa chỉ ít nhất 6 kí tự.");
        } else if (inventory.getAddress().trim().length() > 100) {
            errors.rejectValue(fieldAddress, "address.length", "Địa chỉ tốt đa 100 kí tự.");
        } else if (!Pattern.compile(regexAddress).matcher(inventory.getAddress()).find()) {
            errors.rejectValue(fieldAddress, "address.pattern", "Đại chỉ không được nhập ký tự đặt biệt.");
        }
    }
}
