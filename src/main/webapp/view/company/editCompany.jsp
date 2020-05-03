<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${company.companyName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="edit">
    <input type="hidden" value="${company.companyID}" name="companyID">
    <input type="hidden" value="${company.companyName}" name="oldName">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter company name</p>
            </td>
            <td>
                <input type="text" value="${company.companyName}" name="companyName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter company location</p>
            </td>
            <td>
                <input type="text" value="${company.location}" name="location" required pattern=".*\S+.*">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
    <a href="${pageContext.request.contextPath}/company/delete?id=${company.companyID}"
       class="button" role="button" tabindex="0">Delete Company</a>
</form>
<p>${validate}</p>
</body>
</html>