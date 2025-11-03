package com.yourdomain.studyplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // --- 1. Define Standard Environment Variable KEYS (Names) ---
    //    These KEYS (DB_HOST, etc.) are the NAMES we will use in the Render dashboard.
    private static final String DB_HOST_KEY = "DB_HOST";
    private static final String DB_PORT_KEY = "DB_PORT";
    private static final String DB_NAME_KEY = "DB_NAME";
    private static final String DB_USER_KEY = "DB_USER";
    private static final String DB_PASSWORD_KEY = "DB_PASSWORD";
    
    // --- 2. Retrieve VALUES from the OS Environment (System.getenv) ---
    private static final String DB_HOST = System.getenv(DB_HOST_KEY);
    private static final String DB_PORT = System.getenv(DB_PORT_KEY);
    private static final String DB_NAME = System.getenv(DB_NAME_KEY);
    private static final String DB_USER = System.getenv(DB_USER_KEY);
    private static final String DB_PASSWORD = System.getenv(DB_PASSWORD_KEY);

    // --- 3. Construct the URL (Must be done inside getConnection or here) ---
    // Note: We leave this construction here, but the null check is in getConnection()
    private static final String JDBC_URL_BASE = "jdbc:postgresql://";
    
    static {
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading PostgreSQL JDBC driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        // --- CRITICAL NULL CHECK: Prevents crash if Render vars aren't loaded or are missing ---
        if (DB_HOST == null || DB_PORT == null || DB_NAME == null || DB_USER == null || DB_PASSWORD == null) {
            throw new SQLException("Database connection failed: One or more environment variables are missing (DB_HOST, etc.). Check Render configuration.");
        }
        
        // Assemble the final URL string
        String finalJdbcUrl = JDBC_URL_BASE + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

        // --- 4. Pass the Environment Variables (VALUES) to the Driver Manager ---
        return DriverManager.getConnection(finalJdbcUrl, DB_USER, DB_PASSWORD);
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
