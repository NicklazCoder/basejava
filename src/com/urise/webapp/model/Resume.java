package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    protected final Map<ContactType,String> contacts = new EnumMap<>(ContactType.class);
    protected final Map<SectionType,Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(String.valueOf(UUID.randomUUID()), fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContacts(ContactType type) {
        return contacts.get(type);
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }
    public void setContact(ContactType type, String content) {
        contacts.put(type, content);
    }
    public void setSections(SectionType type, Section content) {
        sections.put(type, content);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }
}
