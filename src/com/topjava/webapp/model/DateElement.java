package com.topjava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateElement extends Element {
    private static final long serialVersionUID=1L;

    private final LocalDate from;
    private final LocalDate to;
    private Link link;

    public DateElement(String title, LocalDate from, LocalDate to) {
        super(title);
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        this.from = from;
        this.to = to;
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
        return from.equals(that.from) &&
                to.equals(that.to) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, link);
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
