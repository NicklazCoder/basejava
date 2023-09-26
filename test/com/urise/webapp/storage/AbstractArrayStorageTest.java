package com.urise.webapp.storage;


import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveOverflow() {
        Assertions.assertThrows(StorageException.class, () -> {
            try {
                storage.clear();
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume(Integer.toString(i)));
                }
            } catch (StorageException e) {
                Assertions.fail();
            }
            storage.save(new Resume("dummy"));

        });
    }

}