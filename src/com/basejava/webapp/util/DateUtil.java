package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;


public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM, YYYY");

    public static String toHtml(LocalDate start) {
        return start.format(formatter);
    }

    public static LocalDate toLocalDate(String date) {
        if (date.length() == 8) {
            int month = Integer.parseInt(date.substring(0, 2));
            int year = Integer.parseInt(date.substring(4));
            return of(year, Month.of(month));
        }
        return LocalDate.of(0, 0, 0);
    }
}