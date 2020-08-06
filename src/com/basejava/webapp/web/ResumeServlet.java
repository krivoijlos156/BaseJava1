package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

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
        try {
            Resume r = storage.get(uuid);
            setDetals(request, response, fullName, r);
            storage.update(r);
        } catch (NotExistStorageException e) {
            Resume r = new Resume(uuid);
            setDetals(request, response, fullName, r);
            storage.save(r);
        }
    }

    private void setDetals(HttpServletRequest request, HttpServletResponse response, String fullName, Resume r) throws IOException {
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
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String text = request.getParameter(type.name()+"text");
                        if (text != null && text.trim().length() != 0) {
                            value += text;
                        }
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(Arrays.asList(value)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        if (value.equals("name")) {
                            break;
                        }
                        String link = request.getParameter("link");
                        LocalDate dateSt = toLocalDate(request.getParameter("dateSt"));
                        LocalDate dateEnd = toLocalDate(request.getParameter("dateEnd"));
                        String title = request.getParameter("title");
                        String description = request.getParameter("description");
                        r.addSection(type,
                                new OrganizationSection(Arrays.asList(
                                        new Organization(value, link,
                                                new Organization.Position(dateSt, dateEnd, title, description)))));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
            } else {
                r.getContacts().remove(type);
            }
        }
        response.sendRedirect("resume");
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