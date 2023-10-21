package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer serializer;

    protected PathStorage(String dir, StreamSerializer serializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        Objects.requireNonNull(serializer, "directory must not be null");
        this.serializer = serializer;

        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path read error ", getFileName(file), e);
        }
    }

    @Override
    protected List<Resume> doCopy() {
        return getFileList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + getFileName(file), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path update error" + getFileName(file), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        if (!Files.isWritable(file) || !Files.isReadable(file))
            throw new StorageException("DELETE: You don't have permission for this operation", getFileName(file));
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileName(file), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    public void clear() {
        getFileList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFileList().count();
    }

    private Stream<Path> getFileList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Read error", e);
        }
    }

    private String getFileName(Path file) {
        return file.getFileName().toString();
    }
}
