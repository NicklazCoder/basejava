package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(final String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        int position = -index - 1;
        System.arraycopy(storage, position, storage, position + 1, size - index);
        storage[position] = r;
    }

    @Override
    protected void deleteResume(int index) {
        int length = size - index;
        if (length > 0) {
            System.arraycopy(storage, index + 1, storage, index, length);
        }
    }
}
