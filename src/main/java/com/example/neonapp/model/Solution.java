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
    private String AssignmentNo;
    private String subjectName;
    private String fileName;

    public Solution() {}

    public Solution(String studentName, String solutionNo, String subjectName, String fileName) {
        this.studentName = studentName;
        this.AssignmentNo = solutionNo;
        this.subjectName = subjectName;
        this.fileName = fileName;
    }

    // Getters and setters...
}
