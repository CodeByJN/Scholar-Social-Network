<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
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
        input, select {
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
    </style>
</head>
<body>
<h2>User Registration</h2>
<form action="/Scholar_Social_Network_war/register" method="post">
    <label for="userType">User Type:</label>
    <select name="userType" id="userType" required>
        <option value="" disabled selected>Select User Type</option>
        <option value="AcademicProfessional">Academic Professional</option>
        <option value="AcademicInstitution">Academic Institution</option>
    </select>
    <label for="name">Name:</label>
    <input type="text" name="name" id="name" required>
    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>
    <div id="extraFields"></div>
    <button type="submit">Register</button>
</form>
<script>
    document.getElementById("userType").addEventListener("change", function () {
        const extraFields = document.getElementById("extraFields");
        extraFields.innerHTML = "";

        if (this.value === "AcademicProfessional") {
            extraFields.innerHTML = `
                    <label for="currentInstitution">Current Institution:</label>
                    <input type="text" name="currentInstitution" id="currentInstitution" required>
                    <label for="academicPosition">Academic Position:</label>
                    <input type="text" name="academicPosition" id="academicPosition" required>
                `;
        } else if (this.value === "AcademicInstitution") {
            extraFields.innerHTML = `
                    <label for="institutionName">Institution Name:</label>
                    <input type="text" name="institutionName" id="institutionName" required>
                `;
        }
    });
</script>
</body>
</html>
