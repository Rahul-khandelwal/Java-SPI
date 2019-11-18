/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul.spi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author in-rahul.khandelwal
 */
public class Utility {

    public static String getFormattedCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getOneYearLaterDate(String format) {
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static int calculateAge(String date, String format) {
        try {
            var dob = new SimpleDateFormat(format).parse(date);
            var now = new Date();

            var diff = now.getTime() - dob.getTime();
            return (int) (diff / (1000l * 3600l * 24l * 365l));
        } catch (ParseException e) {
            return -1;
        }
    }

    public static String reformatDate(String date, String formatFrom, String formatTo) {
        try {
            return new SimpleDateFormat(formatTo).format(new SimpleDateFormat(formatFrom).parse(date));
        } catch (ParseException e) {
            return null;
        }
    }
}
