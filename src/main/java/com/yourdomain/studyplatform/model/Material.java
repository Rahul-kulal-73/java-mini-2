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
    
    // Constructors (as provided before)
    // ...

    // --- MUST HAVE: ALL Getters and Setters to resolve compilation errors ---
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    // *** THESE ARE THE MISSING METHODS CAUSING THE ERROR ***
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
