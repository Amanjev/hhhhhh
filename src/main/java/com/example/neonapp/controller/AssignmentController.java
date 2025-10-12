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
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        assignment.setCreatedAt(LocalDateTime.now());
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return ResponseEntity.ok(savedAssignment);
    }

    // ✅ Get all assignments
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        return assignmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete assignment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
            return ResponseEntity.ok("Assignment deleted successfully");
        }
        return ResponseEntity.status(404).body("Assignment not found");
    }
}
