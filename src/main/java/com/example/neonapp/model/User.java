package com.example.neonapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String batch;
    private String course;
    private String enrollment;

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getEnrollment() { return enrollment; }
    public void setEnrollment(String enrollment) { this.enrollment = enrollment; }
}