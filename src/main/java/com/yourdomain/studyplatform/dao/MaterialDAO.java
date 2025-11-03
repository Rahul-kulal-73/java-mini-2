package com.yourdomain.studyplatform.dao;

import com.yourdomain.studyplatform.model.Material;
import com.yourdomain.studyplatform.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    // SQL Statements - Use the PostgreSQL table names
    private static final String INSERT_MATERIAL_SQL = "INSERT INTO materials (user_id, title, subject, description, file_link) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_MATERIALS = "SELECT material_id, user_id, title, subject, description, file_link, upload_date FROM materials ORDER BY upload_date DESC";
    private static final String DELETE_MATERIAL_SQL = "DELETE FROM materials WHERE material_id = ? AND user_id = ?";

    // --- 1. CREATE ---
    public void insertMaterial(Material material) throws SQLException {
        // No change needed: Connection is inside try-with-resources.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MATERIAL_SQL)) {
            preparedStatement.setInt(1, material.getUserId());
            preparedStatement.setString(2, material.getTitle());
            preparedStatement.setString(3, material.getSubject());
            preparedStatement.setString(4, material.getDescription());
            preparedStatement.setString(5, material.getFileLink());
            preparedStatement.executeUpdate();
        }
    }

    // --- 2. READ (All) ---
    public List<Material> selectAllMaterials() throws SQLException { // <-- ADDED: Throws SQLException
        List<Material> materials = new ArrayList<>();
        // No change needed: Connection is inside try-with-resources.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MATERIALS);
             ResultSet rs = preparedStatement.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("material_id");
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String subject = rs.getString("subject");
                String description = rs.getString("description");
                String fileLink = rs.getString("file_link");
                // CRITICAL: Ensure the LocalDate object creation is safe
                Date uploadSqlDate = rs.getDate("upload_date");
                LocalDate uploadDate = (uploadSqlDate != null) ? uploadSqlDate.toLocalDate() : null;
                
                materials.add(new Material(id, userId, title, subject, description, fileLink, uploadDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // <-- CRITICAL CHANGE: Re-throw the SQL Exception to be handled upstream
        }
        return materials;
    }

    // --- 3. DELETE (Securely by Material ID AND User ID) ---
    public boolean deleteMaterial(int materialId, int userId) throws SQLException {
        boolean rowDeleted;
        // No change needed: Connection is inside try-with-resources.
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_MATERIAL_SQL)) {
            statement.setInt(1, materialId);
            statement.setInt(2, userId); // SECURITY: Only delete if the user owns it
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
