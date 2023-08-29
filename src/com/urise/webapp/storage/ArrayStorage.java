package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r != null) {
            int index = getIndex(r.getUuid());
            if (index == -1) {
                if (size >= STORAGE_LIMIT) {
                    System.out.println("Storage overflow!");
                } else {
                    storage[size] = r;
                    size++;
                }
            } else {
                System.out.println("Resume with uuid = " + r.getUuid() + " is exist");
            }
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume with uuid = " + uuid + " not found");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
    }

    public void update(Resume r) {
        if (r != null) {
            int index = getIndex(r.getUuid());
            if (index != -1) {
                storage[index] = r;
                System.out.println("Resume with uuid = " + r.getUuid() + " was successfully updated");
            } else {
                System.out.println("Resume with uuid = " + r.getUuid() + " is not exist");
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(final String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}