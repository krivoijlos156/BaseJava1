<%@ page import="com.basejava.webapp.model.Section" %>
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
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Раздел</th>
                <th></th>
            </tr>
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.SectionType,
                         com.basejava.webapp.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
            <tr>
                <td width="150"><%=sectionEntry.getKey().getTitle() + ":"%>
                </td>
                <td width="450">
                    <%=sectionEntry.getValue().toHtml(sectionEntry.getValue())%> <br/>
                </td>
            </tr>
            </c:forEach>
            </c:if>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>