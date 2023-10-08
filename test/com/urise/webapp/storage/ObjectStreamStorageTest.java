package com.urise.webapp.storage;

class ObjectStreamStorageTest extends AbstractStorageTest {

    ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}