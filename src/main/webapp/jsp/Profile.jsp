<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h2 class="page-title">User Profile</h2>

    <form action="${pageContext.request.contextPath}/profile/update" method="post" class="profile-form">
        <!-- Add hidden field to submit user type -->
        <input type="hidden" name="userType" value="${userType}" />

        <!-- AcademicProfessional User Type -->
        <c:if test="${userType == 'AcademicProfessional'}">
            <div class="form-group">
                <label for="email">Email:</label>
                <span class="read-only-field" id="academic-email">${profile.email}</span>
            </div>

            <div class="form-group">
                <label for="name">Name:</label>
                <span class="read-only-field" id="name">${profile.name != null ? profile.name : 'N/A'}</span>
            </div>

            <div class="form-group">
                <label for="currentInstitution">Current Institution:</label>
                <span class="read-only-field" id="currentInstitution">${profile.currentInstitution != null ? profile.currentInstitution : 'N/A'}</span>
            </div>

            <div class="form-group">
                <label for="academicPosition">Academic Position:</label>
                <span class="read-only-field" id="academicPosition">${profile.academicPosition != null ? profile.academicPosition : 'N/A'}</span>
            </div>

            <div class="form-group">
                <label for="educationBackground">Education Background:</label>
                <input type="text" name="educationBackground" id="educationBackground" value="${profile.educationBackground != null ? profile.educationBackground : ''}" required>
            </div>

            <div class="form-group">
                <label for="areaOfExpertise">Area of Expertise:</label>
                <input type="text" name="areaOfExpertise" id="areaOfExpertise" value="${profile.areaOfExpertise != null ? profile.areaOfExpertise : ''}" required>
            </div>
        </c:if>

        <!-- AcademicInstitution User Type -->
        <c:if test="${userType == 'AcademicInstitution'}">
            <div class="form-group">
                <label for="email">Email:</label>
                <span class="read-only-field" id="email">${profile.email}</span>
            </div>

            <div class="form-group">
                <label for="institutionName">Institution Name:</label>
                <span class="read-only-field" id="institutionName">${profile.institutionName != null ? profile.institutionName : 'N/A'}</span>
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" name="address" id="address" value="${profile.address != null ? profile.address : ''}">
            </div>

            <div class="form-group">
                <label for="coursesOfferedByTerm">Courses Offered By Term:</label>
                <input type="text" name="coursesOfferedByTerm" id="coursesOfferedByTerm" value="${profile.coursesOfferedByTerm != null ? profile.coursesOfferedByTerm : ''}">
            </div>
        </c:if>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Update Profile</button>
        </div>
    </form>

    <div class="message" style="color: ${messageColor};">
        ${message}
    </div>

    <a href="${pageContext.request.contextPath}/jsp/Login.jsp" class="back-link">Back to Home</a>
</div>
</body>
</html>
