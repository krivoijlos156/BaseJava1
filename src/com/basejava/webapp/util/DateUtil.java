package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * gkislin
 * 20.07.2016
 */
public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }
}
