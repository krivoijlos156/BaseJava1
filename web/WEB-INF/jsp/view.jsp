<%@ page import="com.basejava.webapp.model.Section" %>
<%@ page import="static com.basejava.webapp.util.DateUtil.toHtml" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:if test="${resume.sections!='{}'}">
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType,
                         com.basejava.webapp.model.Section>"/>
            <c:set var="section" value="${sectionEntry.value}"></c:set>
            <c:set var="type" value="${sectionEntry.key}"></c:set>
            <tr>
                <td><h3><%=sectionEntry.getKey().getTitle() + ":"%>
                </h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL'||type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">${section}
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="${section.items}">
                                    <li>
                                            ${item}
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                    <c:forEach var="organization" items="${section.organizations}">
                        <tr>
                            <td colspan="2">
                                <h4><a href="${organization.homePage.url}"> ${organization.homePage.name}</a></h4>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position"
                                         type="com.basejava.webapp.model.Organization.Position"/>
                            <tr>
                                <td width="15%" style="vertical-align: top">
                                    <%=toHtml(position.getStartDate())%> -
                                    <%=toHtml(position.getEndDate())%>
                                </td>
                                <td>
                                    <b>${position.title}</b><br/>
                                        ${position.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        </c:if>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>