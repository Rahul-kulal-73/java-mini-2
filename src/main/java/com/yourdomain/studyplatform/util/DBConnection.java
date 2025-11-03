package com.yourdomain.studyplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // --- 1. Define variables to read secrets from the OS environment ---
    //    These KEYS (DB_HOST, DB_USER, etc.) MUST match what you set in Render's dashboard.
    private static final String DB_HOST = System.getenv("dpg-d3vfllur433s73crbcrg-a");
    private static final String DB_PORT = System.getenv("5432"); 
    private static final String DB_NAME = System.getenv("java_db_1uqk");
    private static final String DB_USER = System.getenv("java_db_1uqk_user");
    private static final String DB_PASSWORD = System.getenv("bub14XdCPlXsn01iMA2nw0uWzQ2ruQ9c");

    // --- 2. Construct the URL dynamically using the environment variables ---
    //    This is the standard PostgreSQL JDBC format.
    private static final String JDBC_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    
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
        // --- 3. Pass the Environment Variables to the Driver Manager ---
        //    This is the final connection attempt using the secure secrets.
        //    It throws an exception if any variable (HOST, USER, or PASSWORD) is missing or wrong.
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
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
