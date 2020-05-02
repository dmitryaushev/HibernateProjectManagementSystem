<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Developers</title>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<c:import url="navibar.jsp"/>
<c:if test="${not empty developers}">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Developers</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${developers}" var="developer">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/developer/get?id=${developer.developerID}">
                            ${developer.firstName} ${developer.lastName}</a><br>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
