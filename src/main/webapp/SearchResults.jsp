<%-- 
    Document   : SearchResults
    Created on : Nov 29, 2024, 1:33:39 p.m.
    Author     : baljo
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.Course" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <h1>Search Results</h1>
    <%
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        if (courses == null || courses.isEmpty()) {
    %>
        <p>No courses found matching your criteria.</p>
    <%
        } else {
    %>
        <ul>
        <%
            for (Course course : courses) {
                out.println("<li>" + course.getTitle() + " (" + course.getCode() + ") - " + course.getTerm() + "</li>");
            }
        %>
        </ul>
    <%
        }
    %>
    <a href="Search.jsp">Back to Search</a>
</body>
</html>
