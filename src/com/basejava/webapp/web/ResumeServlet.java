package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.util.DateUtil.toLocalDate;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String text = request.getParameter(type.name() + "text");
                    if (!isNull(text)) value += text;
                    r.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> list = new ArrayList<>();
                    if (r.getSection(type) != null) {
                        int listSize = ((ListSection) r.getSection(type)).getItems().size();
                        for (int i = 0; i < listSize; i++) {
                            String item = request.getParameter(type + "_" + i);
                            if (isNull(item)) continue;
                            list.add(item);
                        }
                    }
                    if (!isNull(value)) list.add(value);
                    r.addSection(type, new ListSection(list));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> orgList = new ArrayList<>();
                    if (r.getSection(type) != null) {
                        int orgSize = ((OrganizationSection) r.getSection(type)).getOrganizations().size();
                        for (int i = 0; i < orgSize; i++) {
                            String name = request.getParameter(type + "_name_" + i);
                            if (isNull(name)) continue;
                            String url = request.getParameter(type + "_url_" + i);
                            List<Organization.Position> posList = new ArrayList<>();
                            int posSize = ((OrganizationSection) r.getSection(type)).getOrganization(i).getPositions().size();
                            for (int j = 0; j < posSize; j++) {
                                String dateStart = request.getParameter(i + "" + type + "_dateSt_" + j);
                                String dateEnd = request.getParameter(i + "" + type + "_dateEnd_" + j);
                                String title = request.getParameter(i + "" + type + "_title_" + j);
                                if (isNull(dateStart, dateEnd, title)) continue;
                                LocalDate dateSt = toLocalDate(dateStart);
                                LocalDate dateE = toLocalDate(dateEnd);
                                String description = request.getParameter(i + "" + type + "_description_" + j);
                                posList.add(new Organization.Position(dateSt, dateE, title, description));
                            }
                            orgList.add(new Organization(new Link(name, url), posList));
                        }
                    }
                    String url = request.getParameter(type + "_url");
                    String dateStart = request.getParameter(type + "_dateSt");
                    String dateEnd = request.getParameter(type + "_dateEnd");
                    String title = request.getParameter(type + "_title");
                    if (!isNull(value, dateStart, dateEnd, title)) {
                        LocalDate dateSt = toLocalDate(dateStart);
                        LocalDate dateE = toLocalDate(dateEnd);
                        String description = request.getParameter(type + "_description");
                        orgList.add(new Organization(value, url, new Organization.Position(dateSt, dateE, title, description)));
                    }
                    r.addSection(type, new OrganizationSection(orgList));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        response.sendRedirect("resume");
        storage.update(r);
    }

    private boolean isNull(String value) {
        if (value == null) {
            return true;
        } else return value.trim().length() == 0;
    }

    private boolean isNull(String... values) {
        for (String s : values) {
            if (isNull(s)) return true;
        }
        return false;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/resumeList.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = new Resume("Enter your name");
                storage.save(r);
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}