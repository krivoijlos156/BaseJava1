package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.basejava.webapp.model.ContactType.*;
import static com.basejava.webapp.model.SectionType.*;
import static com.basejava.webapp.util.DateUtil.of;

public class ResumeTestData {
    private static final String objectiveContent = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    private static final String personalContent = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
    private static final ArrayList<String> achievementContentList = new ArrayList<>();
    private static final ArrayList<String> qualificationsContentList = new ArrayList<>();
    private static final ArrayList<String> experienceContentList = new ArrayList<>();
    private static final ArrayList<LocalDate> experienceDataList = new ArrayList<>();
    private static final ArrayList<String> educationContentList = new ArrayList<>();
    private static final ArrayList<LocalDate> educationDataList = new ArrayList<>();

    static {
        achievementContentList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementContentList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementContentList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementContentList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementContentList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");

        qualificationsContentList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsContentList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsContentList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        experienceContentList.add("Java Online Projects");
        experienceContentList.add("www.javaops.ru/");
        experienceContentList.add("Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceDataList.add(of(2013, Month.OCTOBER));
        experienceDataList.add(LocalDate.now());

        experienceContentList.add("Wrik");
        experienceContentList.add("www.wrike.com/");
        experienceContentList.add("Старший разработчик (backend). Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceDataList.add(of(2014, Month.OCTOBER));
        experienceDataList.add(of(2016, Month.JANUARY));

        experienceContentList.add("RIT Center");
        experienceContentList.add(null);
        experienceContentList.add("Java архитектор. Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        experienceDataList.add(of(2012, Month.APRIL));
        experienceDataList.add(of(2014, Month.OCTOBER));

        educationContentList.add("Coursera");
        educationContentList.add("www.coursera.org");
        educationContentList.add("Functional Programming Principles in Scala\" by Martin Odersky");
        educationDataList.add(of(2013, Month.MARCH));
        educationDataList.add(of(2013, Month.MAY));

        educationContentList.add("Luxoft");
        educationContentList.add("www.luxoft-training.ru");
        educationContentList.add("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        educationDataList.add(of(2011, Month.NOVEMBER));
        educationDataList.add(of(2011, Month.APRIL));

        educationContentList.add("Siemens AG");
        educationContentList.add("www.siemens.ru");
        educationContentList.add("3 месяца обучения мобильным IN сетям (Берлин)");
        educationDataList.add(of(2015, Month.JANUARY));
        educationDataList.add(of(2015, Month.APRIL));
    }

    public static void main(String[] args) {

        Resume r1 = fillResume("uuid1", "Name1");
        printContactResume(r1);
        printContentResume(r1);
    }

    public static Resume fillResume(String uuid, String name) {
        Resume r1 = new Resume(uuid, name);
        r1.setContact(EMAIL, "gkislin@yandex.ru");
        r1.setContact(PHONE, "+7(921) 855-0482");
        r1.setContact(SKYPE, "grigory.kislin");
        r1.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r1.setContact(GITHUB, "https://github.com/gkislin");
        r1.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");

        ArrayList<IElement> objective = createListElements(objectiveContent);
        r1.setSection(OBJECTIVE, objective);

        ArrayList<IElement> personal = createListElements(personalContent);
        r1.setSection(PERSONAL, personal);

        ArrayList<IElement> achievement = createListElements(achievementContentList);
        r1.setSection(ACHIEVEMENT, achievement);

        ArrayList<IElement> qualifications = createListElements(qualificationsContentList);
        r1.setSection(QUALIFICATIONS, qualifications);

        ArrayList<IElement> experience = createListElements(experienceContentList, experienceDataList);
        r1.setSection(EXPERIENCE, experience);

        ArrayList<IElement> education = createListElements(educationContentList, educationDataList);
        r1.setSection(EDUCATION, education);
        return r1;
    }

    private static ArrayList<IElement> createListElements(String content) {
        ArrayList<IElement> list = new ArrayList<>();
        list.add(new Element(content));
        return list;
    }

    private static ArrayList<IElement> createListElements(List<String> listContent) {
        ArrayList<IElement> list = new ArrayList<>();
        for (String content : listContent) {
            list.add(new Element(content));
        }
        return list;
    }

    private static ArrayList<IElement> createListElements(List<String> listContent, List<LocalDate> dateList) {
        ArrayList<IElement> list = new ArrayList<>();
        for (int i = 0, j = 0; i < listContent.size(); i = i + 3, j = j + 2) {
            DateElement element = new DateElement(listContent.get(i), dateList.get(j), dateList.get(j + 1));
            element.setLink(createLink(listContent, i));
            element.setDescription(listContent.get(i + 2));
            list.add(element);
            if ((i + 3) <= listContent.size()) break;
            if (listContent.get(i + 3).startsWith("2")) {
                DateElement element2 = new DateElement(null, dateList.get(j + 2), dateList.get(j + 3));
                element2.setDescription(listContent.get(i + 3).substring(2));
                list.add(element2);
                i++;
                j = j + 2;
            }
        }
        return list;
    }

    private static Link createLink(List<String> listContent, int i) {
        String s = listContent.get(i + 1);
        int pointIndex = s.lastIndexOf('.');
        int wwwIndex = s.indexOf("www");
        return new Link(s.substring(wwwIndex + 4, pointIndex), s.substring(pointIndex + 1));
    }


    private static void printContentResume(Resume r) {
        for (Map.Entry<SectionType, ArrayList<IElement>> entry : r.sections.entrySet()) {
            System.out.println(entry.getKey());
            for (IElement element : entry.getValue()) {
                System.out.println(element);
            }
            System.out.println("");
        }
    }

    private static void printContactResume(Resume r) {
        for (Map.Entry<ContactType, String> entry : r.contacts.entrySet()) {
            System.out.println(entry);
        }
        System.out.println("" + '\n');
    }
}