package com.yourdomain.studyplatform.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Map this servlet to the application root (/). This is the guaranteed entry point.
@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
