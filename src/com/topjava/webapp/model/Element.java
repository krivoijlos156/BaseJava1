package com.topjava.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Element implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String title;
    protected String description;

    public Element(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return title.equals(element.title) &&
                Objects.equals(description, element.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    @Override
    public String toString() {
        if (description == null) {
            return title;
        }
        return title + ", " + '\n' + description;
    }
}