<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
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
<table>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th width="150">Раздел</th>
                <th width="450"></th>
            </tr>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <tr>
                    <td>${type.title}</td>
                    <c:choose>
                        <c:when test="${type=='PERSONAL'||type=='OBJECTIVE'}">
                            <td>
                                <input type="text" name="${type.name()}" size=100 value="${section.toString()}">
                            </td>
                        </c:when>
                        <c:when test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                            <td>
                                <c:if test="${section.items==null}">
                                    <input type="text" name="${type.name()}" size=100 value="">
                                </c:if>
                                <c:forEach var="item" items="${section.items}">
                                    <input type="text" name="${type.name()}" size=100 value="${item}">
                                </c:forEach>
                            </td>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                            <td>
                                <c:if test="${section.organizations==null}">
                                    <input type="text" name="${type.name()}" size=100 value="">
                                </c:if>
                                <c:forEach var="organization" items="${section.organizations}">
                                    <dl>
                                        <dt>Link</dt>
                                        <dd><input type="text" name="Link" size=100 value="${organization.link}"></dd>
                                    </dl>
                                    <c:forEach var="position" items="${organization.positions}">
                                        <jsp:useBean id="position"
                                                     type="com.basejava.webapp.model.Organization.Position"/>
                                        <dl>
                                            <dt>from-to</dt>
                                            <dd><input type="text" name="data" size=100
                                                       value="${position.startDate + '-' + position.endDate}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Title</dt>
                                            <dd><input type="text" name="Title" size=100 value="${position.title}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Description</dt>
                                            <dd><input type="text" name="Description" size=100
                                                       value="${position.description}"></dd>
                                        </dl>
                                    </c:forEach>

                                    <input type="text" name="${type.name()}" size=100 value="${item}">
                                </c:forEach>
                            </td>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
