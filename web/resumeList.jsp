<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <%
            for (Resume r : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><%=r.getUuid()%>
            </td>
            <td><%=r.getFullName()%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
</body>
</html>