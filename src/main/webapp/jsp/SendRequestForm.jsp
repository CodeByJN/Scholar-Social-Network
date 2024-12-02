<!DOCTYPE html>
<html>
<head>
    <title>Send Course Request</title>
</head>
<body>
<h1>Send Course Request</h1>
<form action="sendRequest" method="post">
    <label for="courseId">Course ID:</label>
    <input type="number" id="courseId" name="courseId" required>
    <br>
    <label for="professionalId">Your Professional ID:</label>
    <input type="number" id="professionalId" name="professionalId" required>
    <br>
    <button type="submit">Send Request</button>
</form>
</body>
</html>
