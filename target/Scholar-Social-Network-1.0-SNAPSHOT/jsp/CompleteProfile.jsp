<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Your Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container"> <!-- Added a container for consistent layout -->
    <h2 class="page-title">Complete Your Profile</h2>

    <form action="${pageContext.request.contextPath}/profile/complete" method="post" class="profile-form">
        <!-- Academic Professional Fields -->
        <div id="academicProfessionalFields" style="display:none;" class="form-group">
            <label for="educationBackground">Education Background:</label>
            <input type="text" name="educationBackground" id="educationBackground" class="form-control">

            <label for="areaOfExpertise">Area of Expertise:</label>
            <input type="text" name="areaOfExpertise" id="areaOfExpertise" class="form-control">
        </div>

        <!-- Academic Institution Fields -->
        <div id="academicInstitutionFields" style="display:none;" class="form-group">
            <label for="address">Address (For Academic Institutions):</label>
            <input type="text" name="address" id="address" class="form-control">

            <label for="coursesOfferedByTerm">Courses Offered By Term (For Institutions):</label>
            <input type="text" name="coursesOfferedByTerm" id="coursesOfferedByTerm" class="form-control">
        </div>

        <button type="submit" class="btn btn-primary">Complete Profile</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/jsp/Login.jsp" class="back-link">Back to Home</a></p>
</div>

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
