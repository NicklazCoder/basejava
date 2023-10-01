package com.urise.webapp.model;

public enum ContactType {
    HOME_PHONE("Домашний телефон"),
    PHONE("Телефон"),
    MOBILE("Мобильный телефон"),
    SKYPE("Skype"),
    MAIL("Email"),
    GITHUB("GitHub"),
    LINKEDIN("Профиль LinkedIn"),
    STACKOVERFLOW("StackOverflow"),
    HOME_PAGE("Домашнаяя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
