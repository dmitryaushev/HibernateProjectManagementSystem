<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Developer</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="get" action="find">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter developer email</p>
            </td>
            <td>
                <input type="text" name="email" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Find</button>
</form>
<p>${message}</p>
</body>
</html>