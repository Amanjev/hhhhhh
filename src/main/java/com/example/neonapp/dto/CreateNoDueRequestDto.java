package com.example.neonapp.dto;

public class CreateNoDueRequestDto {
    private String studentName;
    private String enrollmentNo;
    private String subjectName;

    public CreateNoDueRequestDto() {}

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEnrollmentNo() { return enrollmentNo; }
    public void setEnrollmentNo(String enrollmentNo) { this.enrollmentNo = enrollmentNo; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
}
