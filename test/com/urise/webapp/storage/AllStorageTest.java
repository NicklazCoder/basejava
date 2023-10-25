package com.urise.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class,
        ListStorageTest.class,
        SortedArrayStorageTest.class,
        MapStorageTest.class,
        MapResumeStorageTest.class,
        ObjectFileStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        ObjectPathStorageTest.class,
        DataPathStorageTest.class})

public class AllStorageTest {
}
