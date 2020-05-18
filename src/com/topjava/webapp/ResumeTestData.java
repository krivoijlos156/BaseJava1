package com.topjava.webapp;

import com.topjava.webapp.model.DateElement;
import com.topjava.webapp.model.Element;
import com.topjava.webapp.model.Resume;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.topjava.webapp.model.ContactType.*;
import static com.topjava.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Name1");
        r1.setContact(EMAIL, "gkislin@yandex.ru");
        r1.setContact(PHONE, "+7(921) 855-0482");
        r1.setContact(SKYPE, "grigory.kislin");
        r1.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r1.setContact(GITHUB, "https://github.com/gkislin");
        r1.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");


        Element obj = new Element(null);
        obj.setDescription("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        List<Element> objective = new ArrayList<>();
        objective.add(obj);
        r1.setSection(PERSONAL, objective);


        Element pers = new Element(null);
        pers.setDescription("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        List<Element> personal = new ArrayList<>();
        personal.add(pers);
        r1.setSection(PERSONAL, personal);


        Element achiev1 = new Element(null);
        achiev1.setDescription(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                        " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                        "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                        "Более 1000 выпускников.");
        Element achiev2 = new Element(null);
        achiev1.setDescription(
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        Element achiev3 = new Element(null);
        achiev1.setDescription(
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        Element achiev4 = new Element(null);
        achiev1.setDescription(
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        Element achiev5 = new Element(null);
        achiev1.setDescription(
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        Element achiev6 = new Element(null);
        achiev1.setDescription(
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        List<Element> achievement = new ArrayList<>();
        achievement.add(achiev1);
        achievement.add(achiev2);
        achievement.add(achiev3);
        achievement.add(achiev4);
        achievement.add(achiev5);
        achievement.add(achiev6);
        r1.setSection(PERSONAL, achievement);


        Element qualif1 = new Element(null);
        qualif1.setDescription(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        Element qualif2 = new Element(null);
        qualif2.setDescription(
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        Element qualif3 = new Element(null);
        qualif3.setDescription(
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        List<Element> qualifications = new ArrayList<>();
        qualifications.add(qualif1);
        qualifications.add(qualif2);
        qualifications.add(qualif3);
        r1.setSection(QUALIFICATIONS, qualifications);


        Element exp1 = new DateElement("Java Online Projects", LocalDate.of(2013, 10, 1), LocalDate.now());
        exp1.setDescription("Автор проекта." +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Element exp2 = new DateElement("Wrike", LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01));
        exp2.setDescription("Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Element exp3 = new DateElement("RIT Center", LocalDate.of(2012, 04, 01), LocalDate.of(2014, 10, 01));
        exp3.setDescription("Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        List<Element> experience = new ArrayList<>();
        experience.add(exp1);
        experience.add(exp2);
        experience.add(exp3);
        r1.setSection(EXPERIENCE, experience);


        Element ed1 = new DateElement("Coursera", LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01));
        ed1.setDescription("Functional Programming Principles in Scala\" by Martin Odersky");
        Element ed2 = new DateElement("Luxoft", LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01));
        ed2.setDescription("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        Element ed3 = new DateElement("Siemens AG", LocalDate.of(2015, 1, 01), LocalDate.of(2015, 4, 01));
        ed3.setDescription("3 месяца обучения мобильным IN сетям (Берлин)");
        List<Element> education = new ArrayList<>();
        education.add(ed1);
        education.add(ed2);
        education.add(ed3);
        r1.setSection(EDUCATION, education);
    }
}
