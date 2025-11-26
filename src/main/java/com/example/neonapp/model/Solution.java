package com.example.neonapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String enrollment;      // <--- add this
    private String solutionNo;
    private String subjectName;
    private String fileName;

    public Solution() {}

    public Solution(String studentName, String enrollment, String solutionNo, String subjectName, String fileName) {
        this.studentName = studentName;
        this.enrollment = enrollment;
        this.solutionNo = solutionNo;
        this.subjectName = subjectName;
        this.fileName = fileName;
    }

    // Getters & setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEnrollment() { return enrollment; }
    public void setEnrollment(String enrollment) { this.enrollment = enrollment; }

    public String getSolutionNo() { return solutionNo; }
    public void setSolutionNo(String solutionNo) { this.solutionNo = solutionNo; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
