package com.yourdomain.studyplatform.controller;

// CRITICAL: All imports must be 'jakarta'
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/") // Mapped to the root
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // This servlet is now the guaranteed entry point and directs traffic
        // The AuthFilter will handle logic if the user is already logged in
        response.sendRedirect("login");
    }
}
