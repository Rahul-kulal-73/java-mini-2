<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Study Platform</title>
</head>
<body>
    <h1>Register New Account</h1>
    <p style="color: red;">${errorMessage}</p> 
    
    <form action="register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

        <button type="submit">Register</button>
    </form>
    
    <p>Already have an account? <a href="login">Log in here</a></p>
</body>
</html>