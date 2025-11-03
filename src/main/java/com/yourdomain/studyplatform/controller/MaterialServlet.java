package com.yourdomain.studyplatform.controller;

import com.yourdomain.studyplatform.dao.MaterialDAO;
import com.yourdomain.studyplatform.model.Material;
// ------------------------------------------------------------------
// CRITICAL FIX: Changed javax.servlet imports to JAKARTA.servlet
// ------------------------------------------------------------------
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Retained, though AuthFilter handles most session logic
import java.io.IOException;
import java.sql.SQLException;

// Maps all material-related actions
@WebServlet({"/list", "/new", "/insert", "/delete"}) 
public class MaterialServlet extends HttpServlet {
    private MaterialDAO materialDAO;

    public void init() {
        materialDAO = new MaterialDAO();
    }
    
    // ------------------------------------------------------------------
    // REMOVED: isAuthenticated() method. 
    // The AuthFilter.java handles this globally for cleaner Servlets.
    // ------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ** NO AUTH CHECK HERE ** (AuthFilter ensures only logged-in users reach this point)

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
        
        // ** NO AUTH CHECK HERE ** String action = request.getServletPath();
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
        // Use request.getSession(false) since the AuthFilter guarantees a session exists here
        HttpSession session = request.getSession(false); 
        if (session != null) {
            request.setAttribute("currentUserId", session.getAttribute("userId")); 
        }
        request.getRequestDispatcher("material-list.jsp").forward(request, response);
    }
    
    private void insertMaterial(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Session should be guaranteed by AuthFilter
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
        
        // Session should be guaranteed by AuthFilter
        int userId = (int) request.getSession().getAttribute("userId"); 
        
        // DAO handles the security check (materialId AND userId)
        materialDAO.deleteMaterial(materialId, userId); 
        response.sendRedirect("list");
    }
}
