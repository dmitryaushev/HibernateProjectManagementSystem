<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Projects</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<c:if test="${not empty projects}">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Project</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${projects}" var="project">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/project/get?id=${project.projectID}">
                            ${project.projectName}</a><br>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
