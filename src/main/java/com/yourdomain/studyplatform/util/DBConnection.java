package com.yourdomain.studyplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Define Standard Environment Variable KEYS (must match Render settings)
    private static final String DB_HOST_KEY = "DB_HOST";
    private static final String DB_PORT_KEY = "DB_PORT";
    private static final String DB_NAME_KEY = "DB_NAME";
    private static final String DB_USER_KEY = "DB_USER";
    private static final String DB_PASSWORD_KEY = "DB_PASSWORD";
    
    // Retrieve VALUES from the OS Environment
    private static final String DB_HOST = System.getenv(DB_HOST_KEY);
    private static final String DB_PORT = System.getenv(DB_PORT_KEY);
    private static final String DB_NAME = System.getenv(DB_NAME_KEY);
    private static final String DB_USER = System.getenv(DB_USER_KEY);
    private static final String DB_PASSWORD = System.getenv(DB_PASSWORD_KEY);

    private static final String JDBC_URL_BASE = "jdbc:postgresql://";
    
    static {
        try {
            Class.forName("org.postgresql.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading PostgreSQL JDBC driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        // CRITICAL NULL CHECK: Prevents crash if Render vars aren't loaded or are missing
        if (DB_HOST == null || DB_PORT == null || DB_NAME == null || DB_USER == null || DB_PASSWORD == null) {
            throw new SQLException("Database connection failed: One or more environment variables are missing (DB_HOST, etc.). Check Render configuration.");
        }
        
        String finalJdbcUrl = JDBC_URL_BASE + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

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
