package com.example.vaccination_management.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatPrice {

    private DecimalFormat decimalFormat;

    public FormatPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        decimalFormat = new DecimalFormat("#,##0.## ₫", symbols);
        decimalFormat.setMaximumFractionDigits(0);
    }

    public String formatCurrency(double amount) {
        return decimalFormat.format(amount);
    }
}
