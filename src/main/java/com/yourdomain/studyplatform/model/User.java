package com.yourdomain.studyplatform.model;

/**
 * User Model (POJO)
 */
public class User {
    private int id;
    private String username;
    private String password; // Holds the plain text during login/register, or the hash from DB

    // Default Constructor
    public User() {}

    // Constructor for DB retrieval
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Constructor for registration/login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}