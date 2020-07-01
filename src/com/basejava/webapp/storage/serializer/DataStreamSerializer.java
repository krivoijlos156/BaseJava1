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
                boolean isElement = entry.getValue().get(0).getClass().toString().equals("class com.basejava.webapp.model.Element");
                dos.writeBoolean(isElement);
                int listSize = entry.getValue().size();
                dos.writeInt(listSize);
                dos.writeUTF(entry.getKey().name());
                if (isElement) {
                    for (int i = 0; i < listSize; i++) {
                        dos.writeUTF(entry.getValue().get(i).getTitle());
                    }
                } else {
                    for (int i = 0; i < listSize; i++) {
                        dos.writeUTF(entry.getValue().get(i).getTitle());
                        dos.writeUTF(entry.getValue().get(i).getLink().toString());
                        dos.writeUTF(entry.getValue().get(i).getStartDate().toString());
                        dos.writeUTF(entry.getValue().get(i).getEndDate().toString());
                        dos.writeUTF(entry.getValue().get(i).getDescription());
                    }
                }
            }
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            int sizeListCintacts = dis.readInt();
            for (int i = 0; i < sizeListCintacts; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            while (dis.available() > 0) {
                boolean isElement = dis.readBoolean();
                int sizeElementInSections = dis.readInt();
                SectionType sectionName = SectionType.valueOf(dis.readUTF());
                ArrayList<IElement> sectionNew = new ArrayList<>();
                if (isElement) {
                    for (int i = 0; i < sizeElementInSections; i++) {
                        Element elementNew = new Element(dis.readUTF());
                        sectionNew.add(elementNew);
                    }
                    resume.setSection(sectionName, sectionNew);
                } else {
                    for (int i = 0; i < sizeElementInSections; i++) {
                        String title = dis.readUTF();

                        String link = dis.readUTF();
                        int point=link.indexOf('.');
//                        String[] linkMas = link.split(".",2);
                        Link link1 = new Link(link.substring(0,point), link.substring(point+1));

                        String[] startDate = dis.readUTF().split("-");
                        LocalDate stD = DateUtil.of(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]) );
                        String[] endDate = dis.readUTF().split("-");
                        LocalDate endD = DateUtil.of(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]));

                        DateElement elementNew = new DateElement(title, stD, endD);
                        elementNew.setLink(link1);
                        elementNew.setDescription(dis.readUTF());
                        sectionNew.add(elementNew);
                    }
                    resume.setSection(sectionName, sectionNew);
                }
            }
            // TODO implements sections
            return resume;
        }
    }
}