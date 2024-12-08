<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="navbar">
    <button onclick="window.location.href='${pageContext.request.contextPath}/profile/profile'">Profile Management</button>
    <button onclick="window.location.href='${pageContext.request.contextPath}/Course.jsp'">Course Management</button>
    <button onclick="window.location.href='${pageContext.request.contextPath}views/Search.jsp'">Search and Browse</button>
    <button onclick="window.location.href='${pageContext.request.contextPath}/SubmitCourseRequestServlet'">Request to Teach</button>
    <button onclick="window.location.href='${pageContext.request.contextPath}/notifications'">Notification</button>
    <button class="logout" onclick="window.location.href='${pageContext.request.contextPath}/logout'">Logout</button>
</div>
