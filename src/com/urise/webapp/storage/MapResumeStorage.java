package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Resume uuid) {
        return uuid;
    }

    @Override
    protected List<Resume> doCopy() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
