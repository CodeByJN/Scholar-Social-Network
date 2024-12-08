<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Your Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            width: 300px;
        }
        label {
            font-weight: bold;
        }
        input {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        a {
            color: blue;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h2>Complete Your Profile</h2>

<form action="${pageContext.request.contextPath}/profile/complete" method="post">
    <!-- Academic Professional Fields -->
    <div id="academicProfessionalFields" style="display:none;">
        <label for="educationBackground">Education Background:</label>
        <input type="text" name="educationBackground" id="educationBackground">

        <label for="areaOfExpertise">Area of Expertise:</label>
        <input type="text" name="areaOfExpertise" id="areaOfExpertise">
    </div>

    <!-- Academic Institution Fields -->
    <div id="academicInstitutionFields" style="display:none;">
        <label for="address">Address (For Academic Institutions):</label>
        <input type="text" name="address" id="address">

        <label for="coursesOfferedByTerm">Courses Offered By Term (For Institutions):</label>
        <input type="text" name="coursesOfferedByTerm" id="coursesOfferedByTerm">
    </div>

    <button type="submit">Complete Profile</button>
</form>

<p><a href="${pageContext.request.contextPath}/jsp/Login.jsp">Back to Home</a></p>

<script>
    // Display different forms based on user type
    document.addEventListener("DOMContentLoaded", function () {
        var userType = '<%= session.getAttribute("userType") != null ? session.getAttribute("userType") : "" %>';

        if (userType === "AcademicProfessional") {
            document.getElementById("academicProfessionalFields").style.display = "block";
        } else if (userType === "AcademicInstitution") {
            document.getElementById("academicInstitutionFields").style.display = "block";
        }
    });
</script>
</body>
</html>
