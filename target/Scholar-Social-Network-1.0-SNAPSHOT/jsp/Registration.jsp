<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="registration-container">
    <h2>User Registration</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return redirectAfterSubmit()">
        <div class="form-group">
            <label for="userType">User Type:</label>
            <select name="userType" id="userType" required>
                <option value="" disabled selected>Select User Type</option>
                <option value="AcademicProfessional">Academic Professional</option>
                <option value="AcademicInstitution">Academic Institution</option>
            </select>
        </div>

        <div id="nameField" class="form-group" style="display:none;">
            <label for="name">Name:</label>
            <input type="text" name="name" id="name">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
        </div>

        <div id="extraFields"></div>

        <button type="submit">Register</button>
    </form>
</div>

<script>
    // Redirects to CompleteProfile.jsp page after form submission
    function redirectAfterSubmit() {
        setTimeout(function () {
            window.location.href = "${pageContext.request.contextPath}/jsp/CompleteProfile.jsp";
        }, 3000); // Redirect after 3 seconds
        return true;
    }

    // Shows additional fields based on user type selection
    document.getElementById("userType").addEventListener("change", function () {
        const extraFields = document.getElementById("extraFields");
        const nameField = document.getElementById("nameField");

        extraFields.innerHTML = ""; // Clear extra fields

        if (this.value === "AcademicProfessional") {
            nameField.style.display = "block"; // Show nameField
            nameField.querySelector("input").setAttribute("required", "required"); // Set name as required

            extraFields.innerHTML = `
                <label for="currentInstitution">Current Institution:</label>
                <input type="text" name="currentInstitution" id="currentInstitution" required>
                <label for="academicPosition">Academic Position:</label>
                <input type="text" name="academicPosition" id="academicPosition" required>
            `;
        } else if (this.value === "AcademicInstitution") {
            nameField.style.display = "none"; // Hide nameField
            nameField.querySelector("input").removeAttribute("required"); // Remove required attribute from name

            extraFields.innerHTML = `
                <label for="institutionName">Institution Name:</label>
                <select name="institutionName" id="institutionName" required>
                    <option value="" disabled selected>Select Institution</option>
                    <option value="University A">University A</option>
                    <option value="University B">University B</option>
                    <option value="University C">University C</option>
                </select>
            `;
        }
    });
</script>
</body>
</html>
