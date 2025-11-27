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
            if (!"application/pdf".equals(file.getContentType())) {
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

            // Save file metadata to the database using setters
            // create new Solution via no-arg constructor and setters
Solution solution = new Solution();
solution.setStudentName(studentName);
solution.setSolutionNo(solutionNo);
solution.setSubjectName(subjectName);

// choose the correct setter name for filename in your Solution entity:
// common possibilities: setFilename, setFileName, setOriginalFilename, setFile
// try setFileName first; if your Solution.java uses a different name adjust accordingly.
solution.setFileName(file.getOriginalFilename()); // <-- change method name if compiler complains

solutionRepository.save(solution);


            return ResponseEntity.ok("✅ Solution uploaded successfully and saved at: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

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

    @GetMapping
    public List<Solution> getAllSolutions() {
        return solutionRepository.findAll();
    }
}
