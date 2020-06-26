package com.basejava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateElement extends Element {
    private static final long serialVersionUID = 1L;

    private LocalDate startDate;
    private LocalDate endDate;
    private Link link;

    public DateElement() {
    }

    public DateElement(String title, LocalDate from, LocalDate to) {
        super(title);
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        this.startDate = from;
        this.endDate = to;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateElement that = (DateElement) o;
        return startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, link);
    }

    @Override
    public String toString() {
        DateTimeFormatter fOut = DateTimeFormatter.ofPattern("MM/uuuu", Locale.UK);
        String fromNew = startDate.format(fOut);
        String toNew = endDate.format(fOut);
        return title + '(' + link + ')' + '\n' +
                " c-" + fromNew +
                " до-" + toNew + '\n' +
                description;
    }
}