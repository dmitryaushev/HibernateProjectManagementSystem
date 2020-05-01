<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Company</title>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<c:import url="navibar.jsp"/>
<form method="post" action="findCompany">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter company named</p>
            </td>
            <td>
                <input type="text" name="companyName">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Find</button>
</form>
<p>${message}</p>
</body>
</html>