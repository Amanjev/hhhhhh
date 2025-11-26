package com.example.neonapp.repository;

import com.example.neonapp.model.NoDueRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoDueRequestRepository extends JpaRepository<NoDueRequest, Long> {
    List<NoDueRequest> findByEnrollmentNo(String enrollmentNo);
}
