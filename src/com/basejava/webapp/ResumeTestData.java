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
//        r.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization11", "http://Organization11.ru",
//                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
//        r.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization2", "http://Organization2.ru",
//                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
//        r.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Institute", null,
//                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
//                        new Organization("Organization12", "http://Organization12.ru")));
        return r;
    }

    public static Resume fillB(Resume r) {
        r.addContact(ContactType.MAIL, "mail1@ya.ru");
        r.addContact(ContactType.GITHUB, "gitH");
        r.addContact(ContactType.HOME_PAGE, "HomeDrakon");
        r.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        return r;
    }

    public static Resume fillC(Resume r) {
        r.addContact(ContactType.MAIL, "mail1@ya.ru");
        r.addContact(ContactType.GITHUB, "gitH");
        r.addContact(ContactType.HOME_PAGE, "HomeDrakon");
        r.addSection(SectionType.OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new TextSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        r.addSection(SectionType.ACHIEVEMENT, new ListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
        r.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"));
//        r.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization11", "http://Organization11.ru",
//                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
//        r.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization2", "http://Organization2.ru",
//                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
//        r.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Institute", null,
//                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
//                        new Organization("Organization12", "http://Organization12.ru")));
        return r;
    }
}
