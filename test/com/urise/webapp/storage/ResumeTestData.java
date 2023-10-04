package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.List;

public class ResumeTestData {
    static final String PERSONAL = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
    static final String NAME = "Григорий Кислин";
    static final String MOBILE = "+7(921) 855-0482";
    static final String SKYPE = "grigory.kislin";
    static final String MAIL = "gkislin@yandex.ru";
    static final String LINKEDIN = "https://www.linkedin.com/in/gkislin";
    static final String GITHUB = "https://github.com/gkislin";
    static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    static final String HOME_PAGE = "http://gkislin.ru/";

    static final String OBJECTIVE = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";

    static final String ACHIEVEMENT_1 = "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, " +
            "система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет";
    static final String ACHIEVEMENT_2 = "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
            "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников. ";
    static final String ACHIEVEMENT_3 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, " +
            "Google Authenticator, Jira, Zendesk.";
    static final String ACHIEVEMENT_4 = "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP." +
            " Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
            "интеграция CIFS/SMB java сервера. ";
    static final String ACHIEVEMENT_5 = "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, " +
            "Highstock для алгоритмического трейдинга. ";
    static final String ACHIEVEMENT_6 = "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
            "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django). ";
    static final String ACHIEVEMENT_7 = "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";

    static final String QUALIFICATIONS_1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ";
    static final String QUALIFICATIONS_2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce ";
    static final String QUALIFICATIONS_3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB";
    static final String QUALIFICATIONS_4 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy ";
    static final String QUALIFICATIONS_5 = "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts ";
    static final String QUALIFICATIONS_6 = "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice," +
            " GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ";
    static final String QUALIFICATIONS_7 = "Python: Django. ";
    static final String QUALIFICATIONS_8 = "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js";
    static final String QUALIFICATIONS_9 = "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ";
    static final String QUALIFICATIONS_10 = "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ";
    static final String QUALIFICATIONS_11 = "Инструменты: Maven + plugin development, Gradle, настройка Ngnix";
    static final String QUALIFICATIONS_12 = "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer ";
    static final String QUALIFICATIONS_13 = "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования";
    static final String QUALIFICATIONS_14 = "Родной русский, английский \"upper intermediate\"";

    static final Period ALCATEL_PERIOD = new Period("Инженер по аппаратному и программному тестированию",
            "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
            LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1));
    static final Period SIEMENS_PERIOD = new Period("Разработчик ПО",
            "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
            LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1));
    static final Period ENKATA_PERIOD = new Period("Разработчик ПО",
            "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
            LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1));
    static final Period YOTA_PERIOD = new Period("Ведущий специалист",
            "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
            LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1));
    static final Period LUXOFT_PERIOD = new Period("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
            LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1));
    static final Period RIT_PERIOD = new Period("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
            LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1));
    static final Period WRIKE_PERIOD = new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
            LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1));
    static final Period JAVA_OPS_PERIOD = new Period("Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.",
            LocalDate.of(2013, 10, 1), LocalDate.now());
    static final Period SCHOOL = new Period("", "Закончил с отличием", LocalDate.of(1984, 9, 1),
            LocalDate.of(1987, 6, 1));
    static final Period UNIVERSITY = new Period("Инженер (программист Fortran, C)", "", LocalDate.of(1987, 9, 1),
            LocalDate.of(1993, 7, 1));
    static final Period UNIVERSITY_1 = new Period("Аспирантура (программист С, С++)", "", LocalDate.of(1993, 9, 1),
            LocalDate.of(1996, 7, 1));
    static final Period ALCATEL_PRACTICE = new Period("", "6 месяцев обучения цифровым телефонным сетям (Москва)",
            LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1));
    static final Period SIEMENS_PRACTICE = new Period("", "3 месяца обучения мобильным IN сетям (Берлин)",
            LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1));
    static final Period LUXOFT_PRACTICE = new Period("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", "",
            LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1));
    static final Period COURSERA_PRACTICE = new Period("'Functional Programming Principles in Scala' by Martin Odersky", "",
            LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1));

    static final Organization ALCATEL = new Organization("Alcatel", "http://www.alcatel.ru/", List.of(ALCATEL_PERIOD));
    static final Organization SIEMENS = new Organization("SIEMENS AG", "https://www.siemens.com/ru/ru/home.html", List.of(SIEMENS_PERIOD));
    static final Organization ENKATA = new Organization("Enkata", "http://enkata.com/", List.of(ENKATA_PERIOD));
    static final Organization YOTA = new Organization("Yota", "https://www.yota.ru/", List.of(YOTA_PERIOD));
    static final Organization LUXOFT = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", List.of(LUXOFT_PERIOD));
    static final Organization RIT_CENTER = new Organization("RIT Center", "", List.of(RIT_PERIOD));
    static final Organization WRIKE = new Organization("Wrike", "https://www.wrike.com/", List.of(WRIKE_PERIOD));
    static final Organization JAVA_OPS = new Organization("Java Online Projects", "http://javaops.ru/", List.of(JAVA_OPS_PERIOD));
    static final Organization EDUCATION_SCHOOL = new Organization("Заочная физико-техническая школа при МФТИ", "https://mipt.ru/", List.of(SCHOOL));
    static final Organization EDUCATION_UNIVERSITY = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", List.of(UNIVERSITY, UNIVERSITY_1));
    static final Organization EDUCATION_ALCATEL = new Organization("Alcatel", "http://www.alcatel.ru/", List.of(ALCATEL_PRACTICE));
    static final Organization EDUCATION_SIEMENS = new Organization("Siemens AG", "http://www.siemens.ru/", List.of(SIEMENS_PRACTICE));
    static final Organization EDUCATION_LUXOFT = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", List.of(LUXOFT_PRACTICE));
    static final Organization EDUCATION_COURSERA = new Organization("Coursera", "https://www.coursera.org/course/progfun", List.of(COURSERA_PRACTICE));

    public static void main(String[] args) {
        Resume resume = new Resume(NAME);
        resume.setContact(ContactType.MOBILE, MOBILE);
        resume.setContact(ContactType.SKYPE, SKYPE);
        resume.setContact(ContactType.MAIL, MAIL);
        resume.setContact(ContactType.LINKEDIN, LINKEDIN);
        resume.setContact(ContactType.GITHUB, GITHUB);
        resume.setContact(ContactType.STACKOVERFLOW, STACKOVERFLOW);
        resume.setContact(ContactType.HOME_PAGE, HOME_PAGE);
        resume.setSections(SectionType.OBJECTIVE, new TextSection(OBJECTIVE));
        resume.setSections(SectionType.PERSONAL, new TextSection(PERSONAL));
        resume.setSections(SectionType.ACHIEVEMENT, new ListSection(List.of(ACHIEVEMENT_1, ACHIEVEMENT_2, ACHIEVEMENT_3, ACHIEVEMENT_4, ACHIEVEMENT_5, ACHIEVEMENT_6, ACHIEVEMENT_7)));
        resume.setSections(SectionType.QUALIFICATIONS, new ListSection(List.of(QUALIFICATIONS_1, QUALIFICATIONS_2, QUALIFICATIONS_3, QUALIFICATIONS_4,
                QUALIFICATIONS_5, QUALIFICATIONS_6, QUALIFICATIONS_7, QUALIFICATIONS_8, QUALIFICATIONS_9, QUALIFICATIONS_10, QUALIFICATIONS_11,
                QUALIFICATIONS_12, QUALIFICATIONS_13, QUALIFICATIONS_14)));
        resume.setSections(SectionType.EXPERIENCE, new OrganizationSection(List.of(ALCATEL, SIEMENS, ENKATA, YOTA, LUXOFT, RIT_CENTER, WRIKE, JAVA_OPS)));
        resume.setSections(SectionType.EDUCATION, new OrganizationSection(List.of(EDUCATION_SCHOOL, EDUCATION_UNIVERSITY, EDUCATION_ALCATEL, EDUCATION_SIEMENS, EDUCATION_LUXOFT, EDUCATION_COURSERA)));

        System.out.println(resume.getFullName());
        System.out.println(resume.getContacts(ContactType.MOBILE));
        System.out.println(resume.getContacts(ContactType.SKYPE));
        System.out.println(resume.getContacts(ContactType.MAIL));
        System.out.println(resume.getContacts(ContactType.LINKEDIN));
        System.out.println(resume.getContacts(ContactType.GITHUB));
        System.out.println(resume.getContacts(ContactType.STACKOVERFLOW));
        System.out.println(resume.getContacts(ContactType.HOME_PAGE));
        System.out.println(resume.getSections(SectionType.OBJECTIVE));
        System.out.println(resume.getSections(SectionType.PERSONAL));
        System.out.println(resume.getSections(SectionType.ACHIEVEMENT));
        System.out.println(resume.getSections(SectionType.QUALIFICATIONS));
        System.out.println(resume.getSections(SectionType.EXPERIENCE));
        System.out.println(resume.getSections(SectionType.EDUCATION));
    }

    protected static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.HOME_PHONE, "+375171111111");
        resume.setContact(ContactType.PHONE, "+375171111111");
        resume.setContact(ContactType.MOBILE, "+375291111111");
        resume.setContact(ContactType.SKYPE, "Test.skype");
        resume.setContact(ContactType.MAIL, "Test@gmail.com");
        resume.setContact(ContactType.GITHUB, "https://github.com/JavaWebinar");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/1");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/1/");
        resume.setContact(ContactType.HOME_PAGE, "https://test.com/");
        resume.setSections(SectionType.OBJECTIVE, new TextSection(OBJECTIVE));
        resume.setSections(SectionType.PERSONAL, new TextSection(PERSONAL));
        resume.setSections(SectionType.ACHIEVEMENT, new ListSection(List.of(ACHIEVEMENT_1, ACHIEVEMENT_2, ACHIEVEMENT_3, ACHIEVEMENT_4, ACHIEVEMENT_5, ACHIEVEMENT_6, ACHIEVEMENT_7)));
        resume.setSections(SectionType.QUALIFICATIONS, new ListSection(List.of(QUALIFICATIONS_1, QUALIFICATIONS_2, QUALIFICATIONS_3, QUALIFICATIONS_4,
                QUALIFICATIONS_5, QUALIFICATIONS_6, QUALIFICATIONS_7, QUALIFICATIONS_8, QUALIFICATIONS_9, QUALIFICATIONS_10, QUALIFICATIONS_11,
                QUALIFICATIONS_12, QUALIFICATIONS_13, QUALIFICATIONS_14)));
        resume.setSections(SectionType.EXPERIENCE, new OrganizationSection(List.of(ALCATEL, SIEMENS, ENKATA, YOTA, LUXOFT, RIT_CENTER, WRIKE, JAVA_OPS)));
        resume.setSections(SectionType.EDUCATION, new OrganizationSection(List.of(EDUCATION_SCHOOL, EDUCATION_UNIVERSITY, EDUCATION_ALCATEL, EDUCATION_SIEMENS, EDUCATION_LUXOFT, EDUCATION_COURSERA)));

        return resume;
    }
}
