<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Developers</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="get" action="showSkill">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Developers</th>
            <th>
                <select name="department">
                    <option selected disabled>Department</option>
                    <option>Java</option>
                    <option>C++</option>
                    <option>C#</option>
                    <option>JS</option>
                </select>
            </th>
            <th>
                <select name="level">
                    <option selected disabled>Level</option>
                    <option>Junior</option>
                    <option>Middle</option>
                    <option>Senior</option>
                </select>
            </th>
            <th>
                <button type="submit" class="button">Show</button>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty developers}">
                <c:forEach items="${developers}" var="developer">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/developer/get?id=${developer.developerID}">
                                    ${developer.firstName} ${developer.lastName}</a><br>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <td>No developers yet</td>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</form>
</body>
</html>
