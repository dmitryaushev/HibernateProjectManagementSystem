<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Companies</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<c:if test="${not empty companies}">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Company</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${companies}" var="company">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/company/get?id=${company.companyID}">
                            ${company.companyName}</a><br>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
