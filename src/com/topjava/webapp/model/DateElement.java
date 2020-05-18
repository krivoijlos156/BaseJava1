package com.topjava.webapp.model;

import java.time.LocalDate;
import java.util.Locale;

public class DateElement extends Element {
    LocalDate from;
    LocalDate to;

    public DateElement(String title, LocalDate from, LocalDate to) {
        super(title);
        this.from=from;
        this.to=to;

    }
}
