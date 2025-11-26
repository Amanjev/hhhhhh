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
            @RequestParam("enrollment") String enrollment,
            @RequestParam("subject") String subjectName) {
        try {
            List<Solution> list = solutionRepository.findByEnrollmentAndSubjectName(enrollment, subjectName);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error fetching solutions: " + e.getMessage());
        }
    }
}
