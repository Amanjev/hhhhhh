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

    // Create new assignment
    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            // set creation time
            assignment.setCreatedAt(LocalDateTime.now());
            Assignment savedAssignment = assignmentRepository.save(assignment);
            return ResponseEntity.ok(savedAssignment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error creating assignment: " + e.getMessage());
        }
    }

    // Get all assignments
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

    // Get assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable Long id) {
        try {
            var maybe = assignmentRepository.findById(id);
            if (maybe.isPresent()) {
                return ResponseEntity.ok(maybe.get());
            } else {
                return ResponseEntity.status(404).body("❌ Assignment not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error fetching assignment: " + e.getMessage());
        }
    }

    // Delete assignment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
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

    // Update an existing assignment by ID (PUT - expects full-ish object; null fields won't overwrite)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        try {
            var maybe = assignmentRepository.findById(id);
            if (maybe.isPresent()) {
                Assignment existing = maybe.get();

                // Update only non-null string/date fields
                if (updatedAssignment.getTitle() != null) {
                    existing.setTitle(updatedAssignment.getTitle());
                }
                if (updatedAssignment.getDescription() != null) {
                    existing.setDescription(updatedAssignment.getDescription());
                }
                if (updatedAssignment.getSubject() != null) {
                    existing.setSubject(updatedAssignment.getSubject());
                }
                if (updatedAssignment.getFacultyId() != null) {
                    existing.setFacultyId(updatedAssignment.getFacultyId());
                }
                if (updatedAssignment.getDueDate() != null) {
                    existing.setDueDate(updatedAssignment.getDueDate());
                }

                // boolean: this will set to false if omitted in JSON (primitive boolean default).
                // If you want "only set when provided", use PATCH or change to Boolean in a DTO.
                existing.setActive(updatedAssignment.isActive());

                Assignment saved = assignmentRepository.save(existing);
                return ResponseEntity.ok(saved);
            } else {
                return ResponseEntity.status(404).body("❌ Assignment not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("❌ Error updating assignment: " + e.getMessage());
        }
    }
}
