<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            width: 400px;
        }
        label {
            font-weight: bold;
        }
        input, select, span {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            display: block;
        }
        input[readonly] {
            background-color: #f0f0f0;
            cursor: not-allowed;
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
        .read-only-field {
            background-color: #f0f0f0;
            padding: 10px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<h2>User Profile</h2>

<form action="${pageContext.request.contextPath}/profile/update" method="post">
    <!-- Add hidden field to submit user type -->
    <input type="hidden" name="userType" value="${userType}" />

    <!-- AcademicProfessional User Type -->
    <c:if test="${userType == 'AcademicProfessional'}">
        <label for="email">Email:</label>
        <span class="read-only-field" id="email">${profile.email}</span>

        <label for="name">Name:</label>
        <span class="read-only-field" id="name">${profile.name != null ? profile.name : 'N/A'}</span>

        <label for="currentInstitution">Current Institution:</label>
        <span class="read-only-field" id="currentInstitution">${profile.currentInstitution != null ? profile.currentInstitution : 'N/A'}</span>

        <label for="academicPosition">Academic Position:</label>
        <span class="read-only-field" id="academicPosition">${profile.academicPosition != null ? profile.academicPosition : 'N/A'}</span>

        <label for="educationBackground">Education Background:</label>
        <input type="text" name="educationBackground" id="educationBackground" value="${profile.educationBackground != null ? profile.educationBackground : ''}" required>

        <label for="areaOfExpertise">Area of Expertise:</label>
        <input type="text" name="areaOfExpertise" id="areaOfExpertise" value="${profile.areaOfExpertise != null ? profile.areaOfExpertise : ''}" required>
    </c:if>

    <!-- AcademicInstitution User Type -->
    <c:if test="${userType == 'AcademicInstitution'}">
        <label for="email">Email:</label>
        <span class="read-only-field" id="email">${profile.email}</span>

        <label for="institutionName">Institution Name:</label>
        <span class="read-only-field" id="institutionName">${profile.institutionName != null ? profile.institutionName : 'N/A'}</span>

        <label for="address">Address:</label>
        <input type="text" name="address" id="address" value="${profile.address != null ? profile.address : ''}">

        <label for="coursesOfferedByTerm">Courses Offered By Term:</label>
        <input type="text" name="coursesOfferedByTerm" id="coursesOfferedByTerm" value="${profile.coursesOfferedByTerm != null ? profile.coursesOfferedByTerm : ''}">
    </c:if>

    <button type="submit">Update Profile</button>
</form>

<div class="message" style="color: ${messageColor};">
    ${message}
</div>

<a href="${pageContext.request.contextPath}/jsp/Login.jsp">Back to Home</a>

</body>
</html>
