<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Study Platform</title>
</head>
<body>
    <h1>Study Material Platform Login</h1>
    <p style="color: green;">${successMessage}</p> 
    <p style="color: red;">${errorMessage}</p>
    
    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">Login</button>
    </form>
    
    <p>Don't have an account? <a href="register">Register here</a></p>
</body>
</html>