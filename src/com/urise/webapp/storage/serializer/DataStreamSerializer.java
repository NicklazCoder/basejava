package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            ContactsWriter(r, dos);
            MapWriter(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> TextSectionWriter(dos, (TextSection) section);
                    case ACHIEVEMENT, QUALIFICATIONS -> ListSectionWriter(dos, (ListSection) section);
                    case EDUCATION, EXPERIENCE -> OrganizationSectionWriter(dos, (OrganizationSection) section);
                }
            });
        }
    }

    private void OrganizationSectionWriter(DataOutputStream dos, OrganizationSection section) {
        MapWriter(dos, section.getOrganizations(), entryOrg -> {
            dos.writeUTF(entryOrg.getName());
            dos.writeUTF(entryOrg.getHomePage());
            MapWriter(dos, entryOrg.getPeriods(), entryPeriods -> {
                dos.writeUTF(entryPeriods.getTitle());
                dos.writeUTF(entryPeriods.getDescription());
                dos.writeUTF(entryPeriods.getStartDate().toString());
                dos.writeUTF(entryPeriods.getEndDate().toString());
            });

        });
    }

    private void ListSectionWriter(DataOutputStream dos, ListSection section) {
        MapWriter(dos, section.getElements(), dos::writeUTF);
    }

    private void TextSectionWriter(DataOutputStream dos, TextSection section) throws IOException {
        dos.writeUTF(section.getContent());
    }

    private void ContactsWriter(Resume r, DataOutputStream dos) throws IOException {
        dos.writeUTF(r.getUuid());
        dos.writeUTF(r.getFullName());
        MapWriter(dos, r.getContacts().entrySet(), entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            ItemReader(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            ItemReader(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                //resume.setSections(type,);
            });
            return resume;
        } catch (IOException e) {
            throw new StorageException("Error read resume", e);
        }
    }


    private interface ItemWriter<T> {
        void write(T o) throws IOException;
    }

    private interface ItemProcess {
        void process() throws IOException;
    }

    private interface ItemReader<T> {
        T read() throws IOException;
    }

    private void ItemReader(DataInputStream dis, ItemProcess processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void MapWriter(DataOutputStream dos, Collection<T> collection, ItemWriter<T> writer) {
        try {
            dos.writeInt(collection.size());
            for (T item : collection) {
                writer.write(item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO
    private Section SectionReader(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return null;
            }
            default -> throw new IllegalStateException();
        }
    }
}
