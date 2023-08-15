package com.example.vaccination_management.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class DateUtils {

    public static int calculateAge(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthDate = format.parse(birthday);
            Date currentDate = new Date();

            long diff = currentDate.getTime() - birthDate.getTime();
            long ageInMilliseconds = Math.abs(diff);

            double years = (double) ageInMilliseconds / (365.25 * 24 * 60 * 60 * 1000);
            int age = (int) years;

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
