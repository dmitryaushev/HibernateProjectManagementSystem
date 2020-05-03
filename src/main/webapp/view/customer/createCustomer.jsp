<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Customer</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="createCustomer">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter customer name</p>
            </td>
            <td>
                <input type="text" name="customerName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter customer location</p>
            </td>
            <td>
                <input type="text" name="location" required pattern=".*\S+.*">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
</form>
<p>${validate}</p>
</body>
</html>