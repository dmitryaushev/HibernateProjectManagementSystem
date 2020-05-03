<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${customer.customerName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Customer</th>
        <th>Location</th>
        <th>Projects</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            ${customer.customerName}
        </td>
        <td>
            ${customer.location}
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty customer.projects}">
                    <c:forEach items="${customer.projects}" var="project">
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
<a href="${pageContext.request.contextPath}/customer/edit?id=${customer.customerID}"
   class="button" role="button" tabindex="0">Edit</a><br>
</body>
</html>