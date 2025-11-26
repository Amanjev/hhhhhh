package com.example.neonapp.dto;

public class ApproveNoDueRequestDto {
    private Long id;                 // optional: prefer id if provided
    private String enrollmentNo;     // optional: used if id not provided
    private String subjectName;      // optional: used if id not provided
    private String approverName;     // optional: who approved (audit)

    public ApproveNoDueRequestDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEnrollmentNo() { return enrollmentNo; }
    public void setEnrollmentNo(String enrollmentNo) { this.enrollmentNo = enrollmentNo; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getApproverName() { return approverName; }
    public void setApproverName(String approverName) { this.approverName = approverName; }
}
