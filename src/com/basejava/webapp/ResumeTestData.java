package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.Month;

public class ResumeTestData {


    public static Resume fill(Resume r) {

        r.addContact(ContactType.MAIL, "mail1@ya.ru");
        r.addContact(ContactType.PHONE, "11111");
        r.addContact(ContactType.SKYPE, "skype.skype");

        r.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        r.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        r.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        r.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        r.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        r.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
        r.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        return r;
    }
}
