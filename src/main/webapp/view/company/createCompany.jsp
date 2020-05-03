<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Company</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="createCompany">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter company name</p>
            </td>
            <td>
                <input type="text" name="companyName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter company location</p>
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