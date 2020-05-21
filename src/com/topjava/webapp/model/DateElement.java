package com.topjava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateElement extends Element {
    private LocalDate from;
    private LocalDate to;
    private String link;

    public DateElement(String title, LocalDate from, LocalDate to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        DateTimeFormatter fOut = DateTimeFormatter.ofPattern("MM/uuuu", Locale.UK);
        String fromNew = from.format(fOut);
        String toNew = to.format(fOut);
        return title + '(' + link + ')' + '\n' +
                " c-" + fromNew +
                " до-" + toNew + '\n' +
                description;
    }
}
