<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Resume list</title>
</head>
<body>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Uuid</th>
            <th>FullName</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
        <tr>
            <td>${resume.uuid}
            </td>
            <td>${resume.fullName}
            </td>
        </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>