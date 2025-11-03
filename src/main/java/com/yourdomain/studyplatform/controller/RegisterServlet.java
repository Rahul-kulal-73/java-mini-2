package com.yourdomain.studyplatform.controller;

import com.yourdomain.studyplatform.dao.UserDAO;
import com.yourdomain.studyplatform.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// NOTE: @WebServlet("/register") annotation has been REMOVED
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            User newUser = new User(username, password);
            boolean success = userDAO.registerUser(newUser);

            if (success) {
                // Success: Redirect to login page
                request.setAttribute("successMessage", "Registration successful! Please log in.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Failure: Username already exists (handled in DAO)
                request.setAttribute("errorMessage", "Username already taken. Please choose another.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed due to a server error.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
