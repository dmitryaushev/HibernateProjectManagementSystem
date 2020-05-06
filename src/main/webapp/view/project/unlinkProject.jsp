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
<form method="post" action="unlinkProject">
    <input type="hidden" value="${project.projectID}" name="projectID">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Choose companies</p>
                <p>to delete</p>
            </td>
            <td>
                <select name="companies" multiple>
                        <c:if test="${not empty project.companies}">
                            <c:forEach items="${project.companies}" var="company">
                                <option>
                                        ${company.companyName}
                                </option>
                            </c:forEach>
                        </c:if>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p>Choose customers</p>
                <p>to delete</p>
            </td>
            <td>
                <select name="customers" multiple>
                    <c:if test="${not empty project.customers}">
                        <c:forEach items="${project.customers}" var="customer">
                            <option>
                                    ${customer.customerName}
                            </option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p>Choose developers</p>
                <p>to delete</p>
            </td>
            <td>
                <select name="developers" multiple>
                    <c:if test="${not empty project.developers}">
                        <c:forEach items="${project.developers}" var="developer">
                            <option hidden name="developerID" value="${developer.developerID}">
                                    ${developer.firstName} ${developer.lastName}
                            </option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
    <a href="${pageContext.request.contextPath}/project/unlinkAll?id=${project.projectID}"
       class="button" role="button" tabindex="0">Delete All</a>
</form>
<p>${validate}</p>
</body>
</html>