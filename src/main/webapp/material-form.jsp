<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Study Material</title>
</head>
<body>
    <h1>Upload New Material</h1>
    <a href="list">Back to List</a>
    
    <form action="insert" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br><br>

        <label for="subject">Subject/Course:</label>
        <input type="text" id="subject" name="subject" required><br><br>

        <label for="fileLink">File Link (URL):</label>
        <input type="url" id="fileLink" name="fileLink" placeholder="e.g., Google Drive link" required><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="4" cols="50"></textarea><br><br>

        <button type="submit">Upload Material</button>
    </form>
</body>
</html>