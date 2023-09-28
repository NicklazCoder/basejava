package com.urise.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class,
        ListStorageTest.class,
        SortedArrayStorageTest.class,
        MapStorageTest.class,
        MapResumeStorageTest.class})

public class AllStorageTest {
}
