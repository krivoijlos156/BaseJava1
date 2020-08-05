<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
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
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt><h4>Имя:</h4></dt>
        <dd><input name="fullName" size=50 value="${resume.fullName}"></dd>
    </dl>
    <h2>Контакты:</h2>
    <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
        </dl>
    </c:forEach>
    <h2>Секции:</h2>
    <table cellpadding="2">
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <tr>
                <td><h3>${type.title}</h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL'||type=='OBJECTIVE'}">
                    <tr>
                        <td></td>
                        <td>
                            <input name="${type.name()}" size=100 value="${section.toString()}">
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                    <tr>
                        <td></td>
                        <td>
                            <c:if test="${section.items==null}">
                                <textarea name="${type.name()}" cols="100" rows="4"></textarea>
                            </c:if>
                            <c:forEach var="item" items="${section.items}">
                                <textarea name="${type.name()}" cols="100" rows="4"> ${item}</textarea>
                            </c:forEach>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                    <c:if test="${section.organizations==null}">
                        <tr>
                            <td>
                                <input name="${type.name()}" size=40 value="">
                                <input name="link" size=40 value="">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input name="dateSt" size=20 value="">
                                <input name="dateEnd" size=20 value="">
                            </td>
                            <td>
                                <input name="title" size=100 value="">
                                <textarea name="description" cols="100" rows="3"></textarea>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="organization" items="${section.organizations}">
                        <jsp:useBean id="organization" type="com.basejava.webapp.model.Organization"/>
                        <tr>
                            <td>
                                <dd><input name="${type.name()}" size=40 value="${organization.homePage.name}">
                                    <input name="link" size=40 value="${organization.homePage.url}">
                                </dd>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="com.basejava.webapp.model.Organization.Position"/>
                            <tr>
                                <td>
                                    <input name="dateSt" size=20 value="<%=toHtml(position.getStartDate())%>">
                                    <input name="dateEnd" size=20 value="<%=toHtml(position.getEndDate())%>">
                                </td>
                                <td>
                                    <input name="title" size="60" value="${position.title}">
                                    <textarea name="description" cols="100" rows="3"> ${position.description}</textarea>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
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
