package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> RESUME_COMPARATOR = ((o1, o2) ->
            o1.getFullName().compareTo(o2.getFullName()) != 0 ?
                    o1.getFullName().compareTo(o2.getFullName()) : o1.getUuid().compareTo(o2.getUuid()));

    //private static final Comparator<Resume> RESUME_COMPARATOR1 = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    protected abstract SK getSearchKey(String uuid);

    protected abstract Resume doGet(SK uuid);

    protected abstract List<Resume> doCopy();

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);

    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopy();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
