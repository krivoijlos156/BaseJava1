package com.topjava.webapp.model;

public class Element {
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
    public String toString() {
        if (description == null) {
            return title;
        }
        return title + ", " + '\n' + description;
    }
}

