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
                            <input name="${type}" size=100 value="${section.toString()}">
                        </td>
                    </tr>
                    <c:if test="${section!=null}">
                        <tr>
                            <td></td>
                            <td>
                                <input name="${type}text" size=100 value="">
                            </td>
                        </tr>
                    </c:if>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                    <tr>
                        <td></td>
                        <td>
                            <c:forEach var="item" items="${section.items}" varStatus="count">
                                <textarea name="${type}_${count.index}" cols="100" rows="4"> ${item}</textarea>
                            </c:forEach>
                            <textarea name="${type}" cols="100" rows="4"></textarea>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                    <c:forEach var="organization" items="${section.organizations}" varStatus="count">
                        <jsp:useBean id="organization" type="com.basejava.webapp.model.Organization"/>
                        <tr>
                            <td>
                                <dd><input name="${type}_name_${count.index}" size=40 value="${organization.homePage.name}">
                                    <input name="${type}_url_${count.index}" size=40 value="${organization.homePage.url}">
                                </dd>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organization.positions}" varStatus="count1">
                            <jsp:useBean id="position" type="com.basejava.webapp.model.Organization.Position"/>
                            <tr>
                                <td>
                                    <input name="${count.index}${type}_dateSt_${count1.index}" size=20 value="<%=toHtml(position.getStartDate())%>">
                                    <input name="${count.index}${type}_dateEnd_${count1.index}" size=20 value="<%=toHtml(position.getEndDate())%>">
                                </td>
                                <td>
                                    <input name="${count.index}${type}_title_${count1.index}" size="60" value="${position.title}">
                                    <textarea name="${count.index}${type}_description_${count1.index}" cols="100" rows="3"> ${position.description}</textarea>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    <tr>
                        <td>
                            <input name="${type}" size=40 value="">
                            <input name="${type}_url" size=40 value="">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input name="${type}_dateSt" size=20 value="">
                            <input name="${type}_dateEnd" size=20 value="">
                        </td>
                        <td>
                            <input name="${type}_title" size=100 value="">
                            <textarea name="${type}_description" cols="100" rows="3"></textarea>
                        </td>
                    </tr>
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