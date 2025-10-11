package com.example.neonapp.model;

import jakarta.persistence.*;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String assignmentNo;
    private String subjectName;
    private String fileName;

    public Assignment() {}

    public Assignment(String studentName, String assignmentNo, String subjectName, String fileName) {
        this.studentName = studentName;
        this.assignmentNo = assignmentNo;
        this.subjectName = subjectName;
        this.fileName = fileName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAssignmentNo() { return assignmentNo; }
    public void setAssignmentNo(String assignmentNo) { this.assignmentNo = assignmentNo; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
