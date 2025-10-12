package com.example.neonapp.controller;

import com.example.neonapp.model.Solution;
import com.example.neonapp.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    @Autowired
    private SolutionRepository solutionRepository;

    // Use /tmp folder for Render’s writable temporary directory
    private static final String UPLOAD_DIR = "/tmp/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSolution(
            @RequestParam("studentName") String studentName,
            @RequestParam("solutionNo") String solutionNo,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("file") MultipartFile file) {

        try {
            // Validate file type (must be PDF)
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Only PDF files are allowed.");
            }

            // Validate file size (max 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("File size exceeds 5MB limit.");
            }

            // Ensure upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save file to /tmp/uploads/
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // Save file metadata to the database
            Solution solution = new Solution(studentName, solutionNo, subjectName, file.getOriginalFilename());
            solutionRepository.save(solution);

            return ResponseEntity.ok("✅ Solution uploaded successfully and saved at: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Solution> getAllSolutions() {
        return solutionRepository.findAll();
    }
}
