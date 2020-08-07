package com.basejava.webapp.util;

import java.lang.ref.SoftReference;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(String year, String month) {
        return of(Integer.parseInt(year), Month.of(Integer.parseInt(month)));
    }

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM, YYYY");

    public static String toHtml(LocalDate start) {
        return start.format(formatter);
    }

    public static LocalDate toLocalDate(String date) {
        if (date.trim().length() <= 8 && date.trim().length() >= 6) {
            List<String> list = Arrays.asList("/", ", ", ",", ". ", ".", " ");
            for (String l : list) {
                String[] split = date.split(l);
                if (isTwo(split)) return of(split[1], split[0]);
            }
        }
        return of(0, Month.JANUARY);
    }

    protected static boolean isTwo(String[] split) {
        return split.length == 2;
    }
}