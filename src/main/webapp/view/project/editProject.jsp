<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${project.projectName}</title>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
<c:import url="../navibar.jsp"/>
<form method="post" action="edit">
    <input type="hidden" value="${project.projectID}" name="projectID">
    <input type="hidden" value="${project.projectName}" name="oldName">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Enter project name</p>
            </td>
            <td>
                <input type="text" value="${project.projectName}" name="projectName" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter project status</p>
            </td>
            <td>
                <input type="text" value="${project.status}" name="status" required pattern=".*\S+.*">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter project cost</p>
            </td>
            <td>
                <input type="text" value="${project.cost}" name="cost" required pattern="\d+">
            </td>
        </tr>
        <tr>
            <td>
                <p>Enter project date</p>
            </td>
            <td>
                <input type="text" value="${project.date}" name="date" placeholder="yyyy-mm-dd" required
                       pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])">
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Save</button>
    <a href="${pageContext.request.contextPath}/project/delete?id=${project.projectID}"
       class="button" role="button" tabindex="0">Delete Project</a>
</form>
<p>${validate}</p>
</body>
</html>