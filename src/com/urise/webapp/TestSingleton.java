package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static final TestSingleton ourSingleton = new TestSingleton();

    public static TestSingleton getInstance() {
        return ourSingleton;
    }

    private TestSingleton() {

    }

    public static void main(String[] args) {
        System.out.println(TestSingleton.getInstance().toString());
        for(SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }
}
