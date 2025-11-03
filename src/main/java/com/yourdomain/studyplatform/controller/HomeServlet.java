package com.yourdomain.studyplatform.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/") // Guaranteed entry point
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Check if the user is already logged in
        if (request.getSession(false) != null && request.getSession(false).getAttribute("userId") != null) {
            // If logged in, redirect to the material list
            response.sendRedirect("list");
        } else {
            // If not logged in, redirect to the login page
            response.sendRedirect("login");
        }
    }
}
