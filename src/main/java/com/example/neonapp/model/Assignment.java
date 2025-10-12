package com.example.neonapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String subject;
    private String facultyId;

    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    private boolean isActive;

    public Assignment() {}

    public Assignment(String title, String description, String subject, String facultyId, LocalDateTime createdAt, LocalDateTime dueDate, boolean isActive) {
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.facultyId = facultyId;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.isActive = isActive;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
