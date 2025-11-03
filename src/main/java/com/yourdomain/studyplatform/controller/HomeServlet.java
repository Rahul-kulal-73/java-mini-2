package com.yourdomain.studyplatform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // ANNOTATION IS BACK
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/") // ANNOTATION IS BACK
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            response.sendRedirect("list");
        } else {
            response.sendRedirect("login");
        }
    }
}
