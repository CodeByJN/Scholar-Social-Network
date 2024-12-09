<%--
  course: CST8288
  name: Ningyi Wang
  Student ID: 041120798
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="login-container">
    <h2>User Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
        <button type="submit">Login</button>
    </form>

    <div class="message" style="color: ${messageColor};">
        ${message}
    </div>

    <p>Don't have an account? <a href="${pageContext.request.contextPath}/jsp/Registration.jsp">Register here</a>.</p>

</div>
</body>
</html>
