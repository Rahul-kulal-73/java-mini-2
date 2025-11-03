package com.yourdomain.studyplatform.model;

import java.time.LocalDate;
// Note: You must also create the User.java POJO with ID, username, and password fields.

public class Material {
    private int id;
    private int userId;
    private String title;
    private String subject;
    private String description;
    private String fileLink;
    private LocalDate uploadDate;

    // Default Constructor
    public Material() {}

    // Constructor for creating new materials
    public Material(int userId, String title, String subject, String description, String fileLink) {
        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.fileLink = fileLink;
        this.uploadDate = LocalDate.now();
    }

    // Constructor for fetching existing materials
    public Material(int id, int userId, String title, String subject, String description, String fileLink, LocalDate uploadDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.fileLink = fileLink;
        this.uploadDate = uploadDate;
    }

    // --- Getters and Setters (omitted for brevity, but include all) ---
    public int getId() { return id; }
    // ...
    public String getFileLink() { return fileLink; }
    public LocalDate getUploadDate() { return uploadDate; }
    // ...
}