<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${customer.customerName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="edit">
    <input type="hidden" value="${customer.customerID}" name="customerID">
    <input type="hidden" value="${customer.customerName}" name="oldName">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter customer name</p>
            </td>
            <td>
                <input type="text" value="${customer.customerName}" name="customerName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter customer location</p>
            </td>
            <td>
                <input type="text" value="${customer.location}" name="location" required pattern=".*\S+.*">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
    <a href="${pageContext.request.contextPath}/customer/delete?id=${customer.customerID}"
       class="button" role="button" tabindex="0">Delete customer</a>
</form>
<p>${validate}</p>
</body>
</html>