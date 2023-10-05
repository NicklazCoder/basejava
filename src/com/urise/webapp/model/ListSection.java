package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends Section {
    private final List<String> elements;

    public ListSection(String... elements) {
        this(Arrays.asList(elements));
    }

    public ListSection(List<String> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "elements=" + elements +
                '}';
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return elements != null ? elements.hashCode() : 0;
    }
}
