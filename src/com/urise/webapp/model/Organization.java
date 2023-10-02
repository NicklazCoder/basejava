package com.urise.webapp.model;

import java.util.List;

public class Organization {
    private final String name;
    private final String homePage;
    private final List<Period> periods;

    public Organization(String name, String homePage, List<Period> periods) {
        this.name = name;
        this.homePage = homePage;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public String getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", homePage='" + homePage + '\'' +
                ", periods=" + periods +
                '}';
    }
}
