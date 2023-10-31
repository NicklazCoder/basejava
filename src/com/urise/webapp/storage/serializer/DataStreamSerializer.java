package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeContacts(r, dos);
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(dos, (TextSection) section);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(dos, (ListSection) section);
                    case EDUCATION, EXPERIENCE -> writeOrganizationSection(dos, (OrganizationSection) section);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.setSections(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        dos.writeUTF(r.getUuid());
        dos.writeUTF(r.getFullName());
        writeCollection(dos, r.getContacts().entrySet(), entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private void writeTextSection(DataOutputStream dos, TextSection section) throws IOException {
        dos.writeUTF(section.getContent());
    }

    private void writeListSection(DataOutputStream dos, ListSection section) throws IOException {
        writeCollection(dos, section.getElements(), dos::writeUTF);
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection section) throws IOException {
        writeCollection(dos, section.getOrganizations(), entryOrg -> {
            dos.writeUTF(entryOrg.getName());
            dos.writeUTF(entryOrg.getHomePage());
            writeCollection(dos, entryOrg.getPeriods(), entryPeriods -> {
                dos.writeUTF(entryPeriods.getTitle());
                dos.writeUTF(entryPeriods.getDescription());
                dos.writeUTF(entryPeriods.getStartDate().toString());
                dos.writeUTF(entryPeriods.getEndDate().toString());
            });
        });
    }


    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(dis, dis::readUTF));
            }
            case EDUCATION, EXPERIENCE -> {
                return new OrganizationSection(readList(dis, () ->
                        new Organization(dis.readUTF(), dis.readUTF(),
                                readList(dis, () ->
                                        new Period(dis.readUTF(), dis.readUTF(),
                                                LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()))))));
            }
            default -> throw new IllegalStateException();
        }
    }

    private interface ItemWriter<T> {
        void write(T item) throws IOException;
    }

    private interface ItemProcessor {
        void process() throws IOException;
    }

    private interface ItemReader<T> {
        T read() throws IOException;
    }

    private void readItems(DataInputStream dis, ItemProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ItemWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ItemReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
}
