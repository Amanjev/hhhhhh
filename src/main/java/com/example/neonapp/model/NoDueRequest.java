package com.example.neonapp.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "no_due_request")
public class NoDueRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String enrollmentNo;
    private String subjectName;
    private String status;

    // store timestamps as UTC Instants
    private Instant createdAt;

    // optional decline fields (persisted)
    @Column(name = "reason")
    private String reason;

    @Column(name = "decliner_name")
    private String declinerName;

    @Column(name = "declined_at")
    private Instant declinedAt;

    public NoDueRequest() {}

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEnrollmentNo() { return enrollmentNo; }
    public void setEnrollmentNo(String enrollmentNo) { this.enrollmentNo = enrollmentNo; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDeclinerName() { return declinerName; }
    public void setDeclinerName(String declinerName) { this.declinerName = declinerName; }

    public Instant getDeclinedAt() { return declinedAt; }
    public void setDeclinedAt(Instant declinedAt) { this.declinedAt = declinedAt; }
}
