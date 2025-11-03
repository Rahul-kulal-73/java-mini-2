package com.yourdomain.studyplatform.controller;

import com.yourdomain.studyplatform.dao.MaterialDAO;
import com.yourdomain.studyplatform.model.Material;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

// Maps all material-related actions
@WebServlet({"/list", "/new", "/insert", "/delete"}) 
public class MaterialServlet extends HttpServlet {
    private MaterialDAO materialDAO;

    public void init() {
        materialDAO = new MaterialDAO();
    }
    
    // Simple filter to ensure user is logged in
    private boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login"); // Redirect to LoginServlet
            return false;
        }
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAuthenticated(request, response)) return;

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new":
                    request.getRequestDispatcher("material-form.jsp").forward(request, response);
                    break;
                case "/delete":
                    deleteMaterial(request, response);
                    break;
                case "/list":
                default:
                    listMaterials(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!isAuthenticated(request, response)) return;
        
        String action = request.getServletPath();
        try {
            if (action.equals("/insert")) {
                insertMaterial(request, response);
            } else {
                listMaterials(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listMaterials(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        // Retrieve ALL materials (public sharing)
        request.setAttribute("listMaterials", materialDAO.selectAllMaterials());
        // Also pass the logged-in user's ID for the delete button check in the JSP
        request.setAttribute("currentUserId", request.getSession().getAttribute("userId")); 
        request.getRequestDispatcher("material-list.jsp").forward(request, response);
    }
    
    private void insertMaterial(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int userId = (int) request.getSession().getAttribute("userId");
        
        String title = request.getParameter("title");
        String subject = request.getParameter("subject");
        String description = request.getParameter("description");
        String fileLink = request.getParameter("fileLink");

        Material newMaterial = new Material(userId, title, subject, description, fileLink);
        materialDAO.insertMaterial(newMaterial);
        response.sendRedirect("list");
    }
    
    private void deleteMaterial(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        int materialId = Integer.parseInt(request.getParameter("id"));
        int userId = (int) request.getSession().getAttribute("userId");
        
        // DAO handles the security check (materialId AND userId)
        materialDAO.deleteMaterial(materialId, userId); 
        response.sendRedirect("list");
    }
}