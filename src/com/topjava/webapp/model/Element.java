package com.topjava.webapp.model;

public class Element {
    String title;
    String description;

    public Element(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

