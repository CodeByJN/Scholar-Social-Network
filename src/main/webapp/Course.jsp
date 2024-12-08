<%-- 
    Document   : Course
    Created on : Nov 28, 2024, 1:54:57?p.m.
    Author     : baljo
--%>

<!DOCTYPE html>
<html>
<head>
    <title>Course Management</title>
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
        <a href="EditCourse.jsp"> Update Course</a>
    </nav>

    <h1>Create Course</h1>
    <form action="CourseServlet" method="post">
        <label for="title">Course Title:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="code">Course Code:</label><br>
        <input type="text" id="code" name="code" required><br><br>

        <label for="term">Term:</label><br>
        <input type="text" id="term" name="term" required><br><br>

        <button type="submit">Create Course</button>
    </form>
</body>
</html>
