<%-- 
    Document   : Search
    Created on : Nov 29, 2024, 1:32:08 p.m.
    Author     : baljo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Courses</title>
    <style>
        nav {
            margin-bottom: 20px;
            background-color: #f4f4f4;
            padding: 10px;
        }
        nav a {
            margin-right: 15px;
            text-decoration: none;
            color: #007BFF;
        }
        nav a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <nav>
        <a href="Course.jsp">Course Management</a>
        <a href="Search.jsp">Search Courses</a>
    </nav>
    <h1>Search Courses</h1>
    <form action="${pageContext.request.contextPath}/SearchServlet" method="get">
        <label for="keyword">Search Courses:</label>
        <input type="text" id="keyword" name="keyword" required>
        <button type="submit">Search</button>
    </form>
</body>
</html>
