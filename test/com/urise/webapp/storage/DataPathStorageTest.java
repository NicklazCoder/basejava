package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {

    DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}