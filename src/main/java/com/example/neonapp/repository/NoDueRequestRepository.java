package com.example.neonapp.repository;

import com.example.neonapp.model.NoDueRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoDueRequestRepository extends JpaRepository<NoDueRequest, Long> {
    List<NoDueRequest> findByEnrollmentNo(String enrollmentNo);
    List<NoDueRequest> findByStatus(String status);

    // find single pending request by enrollment & subject
    Optional<NoDueRequest> findFirstByEnrollmentNoAndSubjectNameAndStatus(String enrollmentNo, String subjectName, String status);
    List<NoDueRequest> findBySubjectName(String subjectName);

}
