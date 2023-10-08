package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error ", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("List of files is null, Storage error", null);
        }
        List<Resume> list = new ArrayList<>();
        for (File file : listFiles) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File update error" + file.getAbsolutePath(), file.getName());
        }
    }

    @Override
    protected void doDelete(File file) {
        if (file.canWrite() && file.canRead()) {
            if (!file.delete()) {
                throw new StorageException("File delete error", file.getName());
            }
        } else {
            throw new StorageException("DELETE: You don't have permission for this operation", file.getName());
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                doDelete(file);
            }
        }

    }

    @Override
    public int size() {
        File[] listFile = directory.listFiles();
        if (listFile == null) {
            throw new StorageException("Directory read error", null);
        }
        return listFile.length;
    }
}
