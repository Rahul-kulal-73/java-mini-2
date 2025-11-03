package com.yourdomain.studyplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 1. Use the Hostname, Port, and Database Name from the "Internal" section.
    // Hostname: dsp-d0vlllux43s7bzcbrg-a
    // Database: java_db_luqk
    private static final String JDBC_URL = "jdbc:postgresql://java_db_1uqk_user:bub14XdCPlXsn01iMA2nw0uWzQ2ruQ9c@dpg-d3vfllur433s73crbcrg-a/java_db_1uqk";
    
    // 2. Use the Username
    private static final String JDBC_USER = "java_db_1uqk_user";
    
    // 3. COPY YOUR PASSWORD HERE
    // Password (from your screenshot): bubi6dGPlXlnG1tWA2nwu6ukGr2sODc
    private static final String JDBC_PASSWORD = "bub14XdCPlXsn01iMA2nw0uWzQ2ruQ9c";
    
    static {
        try {
            Class.forName("org.postgresql.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading PostgreSQL JDBC driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        // The URL, User, and Password are used here
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
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