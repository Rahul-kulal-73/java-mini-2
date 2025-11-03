package com.yourdomain.studyplatform.controller;

import com.yourdomain.studyplatform.dao.MaterialDAO;
import com.yourdomain.studyplatform.model.Material;
// CRITICAL FIX: All imports use 'jakarta'
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// Maps all material-related actions
@WebServlet({"/list", "/new", "/insert", "/delete"}) 
public class MaterialServlet extends HttpServlet {
    private MaterialDAO materialDAO;

    public void init() {
        materialDAO = new MaterialDAO();
    }
    
    // Note: Inline isAuthenticated method removed, relying on AuthFilter

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // No authentication check here (AuthFilter handles it)

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
        
        // CRITICAL FIX: Define 'action' locally to fix the compilation error
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
        // ... (body remains the same) ...
        request.setAttribute("listMaterials", materialDAO.selectAllMaterials());
        HttpSession session = request.getSession(false); 
        if (session != null) {
            request.setAttribute("currentUserId", session.getAttribute("userId")); 
        }
        request.getRequestDispatcher("material-list.jsp").forward(request, response);
    }
    
    private void insertMaterial(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // ... (body remains the same, assumes correct implementation of Material constructor) ...
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
        // ... (body remains the same) ...
        int materialId = Integer.parseInt(request.getParameter("id"));
        int userId = (int) request.getSession().getAttribute("userId"); 
        
        materialDAO.deleteMaterial(materialId, userId); 
        response.sendRedirect("list");
    }
}
