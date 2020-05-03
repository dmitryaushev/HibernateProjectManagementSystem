<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Customers</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<c:if test="${not empty customers}">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Customers</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/customer/get?id=${customer.customerID}">
                            ${customer.customerName}</a><br>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
