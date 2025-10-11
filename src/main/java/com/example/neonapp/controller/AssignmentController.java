package com.example.neonapp.controller;

import com.example.neonapp.model.Assignment;
import com.example.neonapp.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // ✅ Use /tmp directory (works on Render)
    private static final String UPLOAD_DIR = "/tmp/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAssignment(
            @RequestParam("studentName") String studentName,
            @RequestParam("assignmentNo") String assignmentNo,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("file") MultipartFile file) {

        try {
            // ✅ Validate file type
            if (file.getContentType() == null || !file.getContentType().equals("application/pdf")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Only PDF files are allowed.");
            }

            // ✅ Validate file size (max 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("File size exceeds 5MB limit (5MB max).");
            }

            // ✅ Create uploads directory if not exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // ✅ Save file to /tmp/uploads/
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // ✅ Save assignment record in the database
            Assignment assignment = new Assignment(studentName, assignmentNo, subjectName, file.getOriginalFilename());
            assignmentRepository.save(assignment);

            return ResponseEntity.ok("Assignment uploaded successfully and saved to: " + filePath);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
}
