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
            } else if (Period.between(production, expiration).getMonths() < 3) {
                errors.rejectValue(fieldExpiration, "expiration.value", "Hạn sử dụng tối thiểu là 3 tháng.");
            } else if (Period.between(production, expiration).getMonths() > 24) {
                errors.rejectValue(fieldExpiration, "expiration.value", "Hạn sử dụng tối đa là 24 tháng.");
            }
        }
    }
}
