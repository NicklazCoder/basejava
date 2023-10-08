package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//TODO Need implement this class
public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path read error ", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        Path[] listPaths = directory.listPaths();
        if (listPaths == null) {
            throw new StorageException("List of files is null, Storage error", null);
        }
        List<Resume> list = new ArrayList<>();
        for (Path file : listPaths) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            doWrite(r, new BufferedOutputStream(new PathOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path update error" + file.getAbsolutePath(), file.getName());
        }
    }

    @Override
    protected void doDelete(Path file) {
        if (file.canWrite() && file.canRead()) {
            if (!file.delete()) {
                throw new StorageException("Path delete error", file.getName());
            }
        } else {
            throw new StorageException("DELETE: You don't have permission for this operation", file.getName());
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }


    }

    @Override
    public int size() {
        Path[] listPath = directory.listPaths();
        if (listPath == null) {
            throw new StorageException("Directory read error", null);
        }
        return listPath.length;
    }
}
