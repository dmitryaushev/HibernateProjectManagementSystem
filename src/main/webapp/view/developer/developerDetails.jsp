<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${developer.firstName} ${developer.lastName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Developer</th>
        <th>Gender</th>
        <th>Age</th>
        <th>Salary</th>
        <th>Email</th>
        <th>Skills</th>
        <th>Projects</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            ${developer.firstName} ${developer.lastName}
        </td>
        <td>
            ${developer.gender}
        </td>
        <td>
            ${developer.age}
        </td>
        <td>
            ${developer.salary} $
        </td>
        <td>
            ${developer.email}
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty developer.skills}">
                    <c:forEach items="${developer.skills}" var="skill">
                        <p>
                                ${skill.department} ${skill.level}
                            <a href="${pageContext.request.contextPath}/developer/deleteSkill?developerID=${developer.developerID}&skillID=${skill.skillID}"
                               class="button1" role="button" tabindex="0">X</a><br>
                        </p>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No skills yet</p>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty developer.projects}">
                    <c:forEach items="${developer.projects}" var="project">
                        <a href="${pageContext.request.contextPath}/project/get?id=${project.projectID}">
                                ${project.projectName}</a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No projects yet</p>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/developer/edit?id=${developer.developerID}"
   class="button" role="button" tabindex="0">Edit</a><br>
</body>
</html>