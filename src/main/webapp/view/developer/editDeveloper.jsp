<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${developer.firstName} ${developer.lastName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="edit">
    <input type="hidden" value="${developer.developerID}" name="developerID">
    <input type="hidden" value="${developer.email}" name="oldEmail">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter first name</p>
            </td>
            <td>
                <input type="text" value="${developer.firstName}" name="firstName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter last name</p>
            </td>
            <td>
                <input type="text" value="${developer.lastName}" name="lastName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter gender</p>
            </td>
            <td>
                <select name="gender">
                    <option>Male</option>
                    <option>Female</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter age</p>
            </td>
            <td>
                <input type="text" value="${developer.age}" name="age" required pattern="\d+">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter salary</p>
            </td>
            <td>
                <input type="text" value="${developer.salary}" name="salary" required pattern="\d+">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter email</p>
            </td>
            <td>
                <input type="text" value="${developer.email}" name="email" required
                       pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">
            </td>
        </tr>
        <tr>
            <td>
                <p>Choose skill</p>
            </td>
            <td>
                <select name="department">
                    <option>Java</option>
                    <option>C++</option>
                    <option>C#</option>
                    <option>JS</option>
                </select>
                <select name="level">
                    <option>Junior</option>
                    <option>Middle</option>
                    <option>Senior</option>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
    <a href="${pageContext.request.contextPath}/developer/delete?id=${developer.developerID}"
       class="button" role="button" tabindex="0">Delete developer</a>
</form>
<p>${validate}</p>
</body>
</html>