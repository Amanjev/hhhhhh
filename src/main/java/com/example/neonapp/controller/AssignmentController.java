package com.example.neonapp.controller;

import com.example.neonapp.model.Assignment;
import com.example.neonapp.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // ✅ Create new assignment
    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            assignment.setCreatedAt(LocalDateTime.now());
            Assignment savedAssignment = assignmentRepository.save(assignment);
            return ResponseEntity.ok(savedAssignment);
        } catch (Exception e) {
            e.printStackTrace(); // Logs to Render console
            return ResponseEntity.internalServerError()
                    .body("❌ Error creating assignment: " + e.getMessage());
        }
    }

    // ✅ Get all assignments
    @GetMapping
    public ResponseEntity<?> getAllAssignments() {
        try {
            List<Assignment> assignments = assignmentRepository.findAll();
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error fetching assignments: " + e.getMessage());
        }
    }

    // ✅ Get assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable Long id) {
        try {
            return assignmentRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).body("Assignment not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error fetching assignment: " + e.getMessage());
        }
    }

    // ✅ Delete assignment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        try {
            if (assignmentRepository.existsById(id)) {
                assignmentRepository.deleteById(id);
                return ResponseEntity.ok("✅ Assignment deleted successfully");
            } else {
                return ResponseEntity.status(404).body("❌ Assignment not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error deleting assignment: " + e.getMessage());
        }
    }
}
