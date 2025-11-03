package com.yourdomain.studyplatform.controller;

import com.yourdomain.studyplatform.dao.UserDAO;
import com.yourdomain.studyplatform.model.User;
// ------------------------------------------------------------------
// CRITICAL FIX: Changed javax.servlet imports to JAKARTA.servlet
// ------------------------------------------------------------------
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.validateUser(username, password);
            if (user != null) {
                // SUCCESS: Create session and store user details
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                
                // Redirect to the Material List Servlet
                response.sendRedirect("list"); 
            } else {
                // FAILURE
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal server error occurred during login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
