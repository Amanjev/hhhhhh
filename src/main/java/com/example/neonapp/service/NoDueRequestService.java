package com.example.neonapp.service;

import com.example.neonapp.dto.ApproveNoDueRequestDto;
import com.example.neonapp.dto.CreateNoDueRequestDto;
import com.example.neonapp.model.NoDueRequest;
import com.example.neonapp.repository.NoDueRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoDueRequestService {

    private final NoDueRequestRepository repo;

    public NoDueRequestService(NoDueRequestRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public NoDueRequest createRequest(CreateNoDueRequestDto dto) {
        NoDueRequest request = new NoDueRequest();
        request.setStudentName(dto.getStudentName());
        request.setEnrollmentNo(dto.getEnrollmentNo());
        request.setSubjectName(dto.getSubjectName());
        request.setStatus("NEW");
        request.setCreatedAt(LocalDateTime.now());
        return repo.save(request);
    }

    public List<NoDueRequest> getAll() {
        return repo.findAll();
    }

    public Optional<NoDueRequest> getById(Long id) {
        return repo.findById(id);
    }

    public List<NoDueRequest> getByEnrollment(String enrollmentNo) {
        return repo.findByEnrollmentNo(enrollmentNo);
    }

    @Transactional
    public Optional<NoDueRequest> updateStatus(Long id, String status) {
        return repo.findById(id).map(req -> {
            req.setStatus(status);
            return repo.save(req);
        });
    }

    // --------------------
    // Approve-related APIs
    // --------------------

    /**
     * Approve by id. Throws 404 if not found.
     */
    @Transactional
    public NoDueRequest approveById(Long id, String approverName) {
        return repo.findById(id).map(req -> {
            req.setStatus("APPROVED");
            // If you later add approver/audit fields, set them here:
            // req.setApproverName(approverName);
            // req.setApprovedAt(LocalDateTime.now());
            return repo.save(req);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "NoDueRequest not found with id: " + id));
    }

    /**
     * Approve the first pending ("NEW") request matching enrollmentNo + subjectName.
     * Throws 404 if none found.
     */
    @Transactional
    public NoDueRequest approveByEnrollmentAndSubject(String enrollmentNo, String subjectName, String approverName) {
        Optional<NoDueRequest> opt = repo.findFirstByEnrollmentNoAndSubjectNameAndStatus(enrollmentNo, subjectName, "NEW");
        NoDueRequest req = opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No pending NoDueRequest found for enrollment=" + enrollmentNo + " subject=" + subjectName));
        req.setStatus("APPROVED");
        // req.setApproverName(approverName);
        // req.setApprovedAt(LocalDateTime.now());
        return repo.save(req);
    }

    /**
     * Convenience method: tries id first, otherwise tries enrollmentNo+subjectName.
     * Throws 400 if input is insufficient.
     */
    @Transactional
    public NoDueRequest approve(ApproveNoDueRequestDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        if (dto.getId() != null) {
            return approveById(dto.getId(), dto.getApproverName());
        }

        if (dto.getEnrollmentNo() != null && dto.getSubjectName() != null) {
            return approveByEnrollmentAndSubject(dto.getEnrollmentNo(), dto.getSubjectName(), dto.getApproverName());
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide id or (enrollmentNo and subjectName)");
    }
}
