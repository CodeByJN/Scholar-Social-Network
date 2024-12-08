<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="navbar">
    <button onclick="window.location.href='${pageContext.request.contextPath}/profile/profile'">Profile Management</button>
    <button onclick="window.location.href='#'">Course Management</button>
    <button onclick="window.location.href='#'">Search and Browse</button>
    <button onclick="window.location.href='#'">Course Request</button>
    <button onclick="window.location.href='#'">Notification</button>
    <button class="logout" onclick="window.location.href='${pageContext.request.contextPath}/logout'">Logout</button>
</div>
