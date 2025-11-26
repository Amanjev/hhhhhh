package com.example.neonapp.controller;

import com.example.neonapp.model.Solution;
import com.example.neonapp.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    @Autowired
    private SolutionRepository solutionRepository;

    // existing upload/getAll endpoints...

    // New endpoint: get solutions by enrollment and subject
    // Example: GET /api/solutions/search?enrollment=ENR001&subject=Math
    @GetMapping("/search")
public ResponseEntity<?> getSolutionsByEnrollmentAndSubject(
        @RequestParam(value = "enrollment", required = false) String enrollment,
        @RequestParam(value = "subject", required = false) String subject) {
    try {
        // Neither param provided -> return all solutions
        if ((enrollment == null || enrollment.isBlank()) && (subject == null || subject.isBlank())) {
            return ResponseEntity.ok(solutionRepository.findAll());
        }

        // Both provided
        if ((enrollment != null && !enrollment.isBlank()) && (subject != null && !subject.isBlank())) {
            return ResponseEntity.ok(solutionRepository.findByEnrollmentAndSubjectName(enrollment, subject));
        }

        // Only enrollment provided
        if (enrollment != null && !enrollment.isBlank()) {
            return ResponseEntity.ok(solutionRepository.findByEnrollment(enrollment));
        }

        // Only subject provided
        return ResponseEntity.ok(solutionRepository.findBySubjectName(subject));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("Error fetching solutions: " + e.getMessage());
    }
}

}
