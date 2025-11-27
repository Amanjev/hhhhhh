package com.example.neonapp.service;

import com.example.neonapp.dto.ApproveNoDueRequestDto;
import com.example.neonapp.dto.CreateNoDueRequestDto;
import com.example.neonapp.dto.DeclineNoDueRequestDto;
import com.example.neonapp.model.NoDueRequest;
import com.example.neonapp.repository.NoDueRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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
        request.setCreatedAt(Instant.now());          // UTC instant
        return repo.save(request);
    }

    public List<NoDueRequest> getAll() {
        return repo.findAll();
    }

    public Optional<NoDueRequest> getById(Long id) {
        return repo.findById(id);
    }

    /**
     * NEW: return the status string for a given NoDueRequest id (throws 404 if missing)
     */
    public String getStatusById(Long id) {
        return repo.findById(id)
                .map(NoDueRequest::getStatus)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "NoDueRequest not found with id: " + id));
    }

    public List<NoDueRequest> getByEnrollment(String enrollmentNo) {
        return repo.findByEnrollmentNo(enrollmentNo);
    }

    public List<NoDueRequest> getBySubject(String subjectName) {
        return repo.findBySubjectName(subjectName);
    }

    @Transactional
    public Optional<NoDueRequest> updateStatus(Long id, String status) {
        return repo.findById(id).map(req -> {
            req.setStatus(status);
            return repo.save(req);
        });
    }

    // Approve-related
    @Transactional
    public NoDueRequest approveById(Long id, String approverName) {
        return repo.findById(id).map(req -> {
            req.setStatus("APPROVED");
            return repo.save(req);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "NoDueRequest not found with id: " + id));
    }

    @Transactional
    public NoDueRequest approveByEnrollmentAndSubject(String enrollmentNo, String subjectName, String approverName) {
        Optional<NoDueRequest> opt = repo.findFirstByEnrollmentNoAndSubjectNameAndStatus(enrollmentNo, subjectName, "NEW");
        NoDueRequest req = opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No pending NoDueRequest found for enrollment=" + enrollmentNo + " subject=" + subjectName));
        req.setStatus("APPROVED");
        return repo.save(req);
    }

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

    // Decline-related
    @Transactional
    public NoDueRequest decline(DeclineNoDueRequestDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required to decline a NoDueRequest");
        }

        return repo.findById(dto.getId()).map(req -> {
            req.setStatus("DECLINED");

            // persist reason/decliner/time (entity must hold these fields of appropriate types)
            req.setReason(dto.getReason());
            req.setDeclinerName(dto.getDeclinerName());
            req.setDeclinedAt(Instant.now());

            return repo.save(req);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "NoDueRequest not found with id: " + dto.getId()));
    }
}
