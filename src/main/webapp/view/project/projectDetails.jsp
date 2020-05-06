<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${project.projectName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Project</th>
        <th>Status</th>
        <th>Cost</th>
        <th>Date</th>
        <th>Companies</th>
        <th>Customers</th>
        <th>Developers</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            ${project.projectName}
        </td>
        <td>
            ${project.status}
        </td>
        <td>
            ${project.cost} $
        </td>
        <td>
            ${project.date}
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty project.companies}">
                    <c:forEach items="${project.companies}" var="company">
                        <a href="${pageContext.request.contextPath}/company/get?id=${company.companyID}">
                                ${company.companyName}</a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No companies yet</p>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty project.customers}">
                    <c:forEach items="${project.customers}" var="customer">
                        <a href="${pageContext.request.contextPath}/customer/get?id=${customer.customerID}">
                                ${customer.customerName}</a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No customers yet</p>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty project.developers}">
                    <c:forEach items="${project.developers}" var="developer">
                        <a href="${pageContext.request.contextPath}/developer/get?id=${developer.developerID}">
                                ${developer.firstName} ${developer.lastName}</a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No developers yet</p>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/project/edit?id=${project.projectID}"
   class="button" role="button" tabindex="0">Edit</a>
<a href="${pageContext.request.contextPath}/project/link?id=${project.projectID}"
   class="button" role="button" tabindex="0">Link</a>
<a href="${pageContext.request.contextPath}/project/unlink?id=${project.projectID}"
   class="button" role="button" tabindex="0">Unlink</a>
</body>
</html>