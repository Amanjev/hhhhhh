package com.example.neonapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String enrollment;
    private String email;

    // Constructors
    public Student() {}

    public Student(String name, String enrollment, String email) {
        this.name = name;
        this.enrollment = enrollment;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEnrollment() { return enrollment; }
    public void setEnrollment(String enrollment) { this.enrollment = enrollment; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
