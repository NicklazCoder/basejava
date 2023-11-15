package com.urise.webapp.storage;

import com.urise.webapp.Config;

import static org.junit.jupiter.api.Assertions.*;

class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDbUrl(), Config.getInstance().getDbUser(), Config.getInstance().getDbPassword()));
    }
}