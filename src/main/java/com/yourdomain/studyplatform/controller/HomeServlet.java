package com.yourdomain.studyplatform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// NOTE: @WebServlet("/") annotation has been REMOVED
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        // Check if the user is already logged in
        if (session != null && session.getAttribute("userId") != null) {
            // If logged in, redirect to the material list
            response.sendRedirect("list");
        } else {
            // If not logged in, redirect to the login page
            response.sendRedirect("login");
        }
    }
}
