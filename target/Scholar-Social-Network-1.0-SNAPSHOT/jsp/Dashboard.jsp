<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="welcome-message-container">
    <div class="welcome-message">
        <p>Welcome, ${sessionScope.user.email}!</p>
    </div>
</div>

</body>
</html>
