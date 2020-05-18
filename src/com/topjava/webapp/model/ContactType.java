package com.topjava.webapp.model;

public enum ContactType {
    EMAIL("email"),
    PHONE ("phone"),
    SKYPE ("Skype"),
    LINKEDIN ("Профиль LinkedIn"),
    GITHUB ("Профиль GitHub"),
    STACKOVERFLOW ("Профиль Stackoverflow");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
