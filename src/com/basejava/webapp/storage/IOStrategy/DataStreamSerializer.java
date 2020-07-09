package com.basejava.webapp.storage.IOStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.basejava.webapp.util.DateUtil.of;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            ArrayList<Map.Entry<ContactType, String>> contactsList = new ArrayList<>(contacts.entrySet());

            writeCollect(dos, contactsList, (i) -> {
                dos.writeUTF(contactsList.get(i).getKey().name());
                dos.writeUTF(contactsList.get(i).getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            ArrayList<Map.Entry<SectionType, Section>> sectionsList = new ArrayList<>(sections.entrySet());
            writeCollect(dos, sectionsList, i -> {
                dos.writeUTF(sectionsList.get(i).getKey().name());
                switch (sectionsList.get(i).getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) sectionsList.get(i).getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> textList = ((ListSection) sectionsList.get(i).getValue()).getItems();
                        writeCollect(dos, textList, j -> dos.writeUTF(textList.get(j)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations =
                                ((OrganizationSection) sectionsList.get(i).getValue()).getOrganizations();
                        writeCollect(dos, organizations, k -> {
                            writeLink(dos, organizations.get(k).getHomePage());
                            List<Organization.Position> positions = organizations.get(k).getPositions();
                            writeCollect(dos, positions, p -> {
                                writeDate(dos, positions.get(p).getStartDate());
                                writeDate(dos, positions.get(p).getEndDate());
                                dos.writeUTF(positions.get(p).getTitle());
                                dos.writeUTF(isNull(positions.get(p).getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    public interface InRotationWrite {
        void doInRotation(int i) throws IOException;
    }

    private <T> void writeCollect(DataOutputStream dos, Collection<T> collection, InRotationWrite doInRotation) throws IOException {
        int colSize = collection.size();
        dos.writeInt(colSize);
        for (int i = 0; i < colSize; i++) {
            doInRotation.doInRotation(i);
        }
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(isNull(link.getUrl()));
    }

    private String isNull(String url) {
        return url != null ? url : "null";
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readCollect(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollect(dis, () -> {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ArrayList<String> textList = new ArrayList<>();
                        readCollect(dis, () -> textList.add(dis.readUTF()));
                        resume.addSection(st, new ListSection(textList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        readCollect(dis, () -> {
                            List<Organization.Position> positionList = new ArrayList<>();
                            Link link = readLink(dis);
                            readCollect(dis, () -> positionList.add(new Organization.Position(
                                    readDate(dis),
                                    readDate(dis),
                                    dis.readUTF(),
                                    toNull(dis.readUTF())
                            )));
                            organizationList.add(new Organization(link, positionList));
                        });
                        OrganizationSection organizationSection = new OrganizationSection(organizationList);
                        resume.addSection(st, organizationSection);
                }
            });
            return resume;
        }
    }

    public interface InRotationRead {
        void doInRotation() throws IOException;
    }

    private void readCollect(DataInputStream dis, InRotationRead doInRotation) throws IOException {
        int colSize = dis.readInt();
        for (int i = 0; i < colSize; i++) {
            doInRotation.doInRotation();
        }
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = toNull(dis.readUTF());
        return new Link(name, url);
    }

    private String toNull(String s) {
        return !s.equals("null") ? s : null;
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.of(dis.readInt());
        return of(year, month);
    }
}