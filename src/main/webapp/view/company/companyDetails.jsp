<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${company.companyName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Company</th>
        <th>Location</th>
        <th>Projects</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            ${company.companyName}
        </td>
        <td>
            ${company.location}
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty company.projects}">
                    <c:forEach items="${company.projects}" var="project">
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
<a href="${pageContext.request.contextPath}/company/edit?id=${company.companyID}"
   class="button" role="button" tabindex="0">Edit</a><br>
</body>
</html>