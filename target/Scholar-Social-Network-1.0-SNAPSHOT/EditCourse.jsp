<%-- 
    Document   : EditCourse
    Created on : Nov 28, 2024, 1:55:51 p.m.
    Author     : baljo
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Course</title>
</head>
<body>
    <h1>Edit Course</h1>
    <form action="EditCourseServlet" method="post">
        <label for="code">Course Code :</label><br>
        <input type="text" id="code" name="code" value="${course.code}" ><br><br>

        <label for="title">Course Title:</label><br>
        <input type="text" id="title" name="title" value="${course.title}" required><br><br>

        <label for="term">Term:</label><br>
        <input type="text" id="term" name="term" value="${course.term}" required><br><br>

        <label for="qualifications">Preferred Qualifications:</label><br>
        <input type="text" id="qualifications" name="qualifications" value="${course.qualifications}"><br><br>

        <button type="submit">Update Course</button>
    </form>
</body>
</html>
