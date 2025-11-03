<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shared Study Materials</title>
    <style>
        .material-item { border: 1px solid #ccc; padding: 15px; margin-bottom: 10px; }
        .material-item h3 { margin-top: 0; }
    </style>
</head>
<body>
    <h1>Study Material Sharing Platform</h1>
    <p>Welcome, ${sessionScope.username}! | <a href="new">Upload New Material</a> | <a href="logout">Logout</a></p>
    
    <c:forEach var="material" items="${listMaterials}">
        <div class="material-item">
            <h3><c:out value="${material.title}" /></h3>
            <p><strong>Subject:</strong> <c:out value="${material.subject}" /></p>
            <p><strong>Uploaded By:</strong> User ID <c:out value="${material.userId}" /> on <c:out value="${material.uploadDate}" /></p>
            <p><c:out value="${material.description}" /></p>
            
            <p>
                <a href="<c:out value='${material.fileLink}' />" target="_blank" style="color: blue;">
                    **View/Download Material**
                </a>
            </p>
            
            <%-- Check: ONLY show delete button if the material's user ID matches the logged-in user's ID --%>
            <c:if test="${material.userId == currentUserId}">
                <a href="delete?id=<c:out value='${material.id}' />" 
                   onclick="return confirm('Are you sure you want to delete this material?');"
                   style="color: red; margin-left: 20px;">
                    Delete My Material
                </a>
            </c:if>
        </div>
    </c:forEach>
    
    <c:if test="${empty listMaterials}">
        <p>No study materials have been uploaded yet.</p>
    </c:if>
</body>
</html>