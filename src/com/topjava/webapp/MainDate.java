package com.topjava.webapp;

import java.time.LocalDate;
import java.time.Month;

import static com.topjava.webapp.util.DateUtil.of;


public class MainDate {
    public static void main(String[] args) {

        LocalDate ld = LocalDate.now();
        LocalDate ld1=of(3, Month.SEPTEMBER);
        System.out.println(ld);
        System.out.println(ld1);

    }
}
