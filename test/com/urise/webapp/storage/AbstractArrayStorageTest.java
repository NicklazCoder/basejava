package com.urise.webapp.storage;


import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = new Resume(UUID_1);
        storage.update(updatedResume);
        Assertions.assertEquals(updatedResume, storage.get(UUID_1));
    }

    @Test
    void save() {
        storage.save(new Resume());
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    void get() {
        Assertions.assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void getAll() {
        Resume[] temp = storage.getAll();
        Assertions.assertEquals(3, temp.length);
        Assertions.assertEquals(RESUME_1, temp[0]);
        Assertions.assertEquals(RESUME_2, temp[1]);
        Assertions.assertEquals(RESUME_3, temp[2]);
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void saveIsExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
    }

    @Test
    void saveOverflow() {
        Assertions.assertThrows(StorageException.class, () -> {
            try {
                for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume());
                }
            } catch (StorageException e) {
                Assertions.fail();
            }
            storage.save(new Resume());

        });
    }
}