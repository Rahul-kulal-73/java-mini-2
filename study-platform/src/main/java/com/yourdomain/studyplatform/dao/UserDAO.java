package com.yourdomain.studyplatform.dao;

import com.yourdomain.studyplatform.model.User;
import com.yourdomain.studyplatform.util.DBConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.*;

public class UserDAO {

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String SELECT_USER_BY_USERNAME = "SELECT user_id, username, password FROM users WHERE username = ?";

    // --- 1. Registration ---
    public boolean registerUser(User user) throws SQLException {
        // Hash the password before storing it securely
        String hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashedPassword);
            
            // Execute the insert. Returns true if one row was affected.
            return preparedStatement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            // Check for unique constraint violation (username already exists)
            if (e.getSQLState().equals("23505")) { // PostgreSQL unique violation error code
                return false;
            }
            throw e; 
        }
    }

    // --- 2. Login Validation ---
    public User validateUser(String username, String password) {
        User user = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                // User found, retrieve stored hashed password
                String storedHash = rs.getString("password");
                
                // Verify the plain text password against the stored hash
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
                
                if (result.verified) {
                    // Password matches! Create and return the user object (without the hash)
                    user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    // Do NOT pass the hash back to the controller/view
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}