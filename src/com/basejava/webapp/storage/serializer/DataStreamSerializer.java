package com.basejava.webapp.storage.serializer;


import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class DataStreamSerializer implements StrategyIO {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            for (Map.Entry<SectionType, ArrayList<IElement>> entry : r.getSections().entrySet()) {
                boolean isElement = entry.getValue().get(0).getClass().toString()
                        .equals("class com.basejava.webapp.model.Element");
                dos.writeBoolean(isElement);
                dos.writeUTF(entry.getKey().name());
                int listSize = entry.getValue().size();
                dos.writeInt(listSize);

                for (int i = 0; i < listSize; i++) {
                    dos.writeUTF(entry.getValue().get(i).getTitle());
                    if (!isElement) {
                        writeUrl(dos, entry.getValue().get(i).getLink());
                        writeDate(dos, entry.getValue().get(i).getStartDate());
                        writeDate(dos, entry.getValue().get(i).getEndDate());
                        dos.writeUTF(entry.getValue().get(i).getDescription());
                    }
                }
            }
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private void writeUrl(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            readPart(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            while (dis.available() > 0) {
                boolean isElement = dis.readBoolean();
                SectionType sectionName = SectionType.valueOf(dis.readUTF());
                ArrayList<IElement> sectionNew = new ArrayList<>();
                if (isElement) {
                    readPart(dis, () -> sectionNew.add(new Element(dis.readUTF())));
                    resume.setSection(sectionName, sectionNew);
                } else {
                    readPart(dis, () -> sectionNew.add(createElement(dis.readUTF(),
                            new Link(dis.readUTF(), dis.readUTF()),
                            DateUtil.of(dis.readInt(), dis.readInt()),
                            DateUtil.of(dis.readInt(), dis.readInt()),
                            dis.readUTF())));
                    resume.setSection(sectionName, sectionNew);
                }
            }
            return resume;
        }
    }

    private interface GoRead {
        void read() throws IOException;
    }

    private void readPart(DataInputStream dis, GoRead goRead) throws IOException {
        int sizeListContacts = dis.readInt();
        for (int i = 0; i < sizeListContacts; i++) {
            goRead.read();
        }
    }

    private DateElement createElement(String title,
                                      Link link,
                                      LocalDate startDate,
                                      LocalDate endDate,
                                      String discription) throws IOException {
        DateElement elementNew = new DateElement(title, startDate, endDate);
        elementNew.setLink(link);
        elementNew.setDescription(discription);
        return elementNew;
    }
}