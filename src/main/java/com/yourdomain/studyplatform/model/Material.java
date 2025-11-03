package com.yourdomain.studyplatform.model;

import java.time.LocalDate;

public class Material {
    private int id;
    private int userId;
    private String title;
    private String subject;
    private String description;
    private String fileLink;
    private LocalDate uploadDate;

    // --- 1. DEFAULT CONSTRUCTOR (REQUIRED by Servlets/JSPs) ---
    public Material() {}

    // --- 2. CREATION CONSTRUCTOR (Used by MaterialServlet for INSERT) ---
    // Matches: int, String, String, String, String
    public Material(int userId, String title, String subject, String description, String fileLink) {
        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.fileLink = fileLink;
        this.uploadDate = LocalDate.now();
    }

    // --- 3. RETRIEVAL CONSTRUCTOR (Used by MaterialDAO for SELECT) ---
    // Matches: int, int, String, String, String, String, LocalDate
    public Material(int id, int userId, String title, String subject, String description, String fileLink, LocalDate uploadDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.fileLink = fileLink;
        this.uploadDate = uploadDate;
    }

    // --- GETTERS and SETTERS (Resolves the 'cannot find symbol' errors) ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getFileLink() { return fileLink; }
    public void setFileLink(String fileLink) { this.fileLink = fileLink; }
    
    public LocalDate getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
}
