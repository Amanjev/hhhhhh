package com.example.neonapp.repository;

import com.example.neonapp.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
    // finds solutions submitted by a specific enrollment and subject
    List<Solution> findByEnrollmentAndSubjectName(String enrollment, String subjectName);

    // Optional helpful methods
    List<Solution> findByEnrollment(String enrollment);
    List<Solution> findBySubjectName(String subjectName);
}
