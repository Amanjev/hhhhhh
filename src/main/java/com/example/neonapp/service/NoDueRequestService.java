package com.example.neonapp.service;

import com.example.neonapp.dto.CreateNoDueRequestDto;
import com.example.neonapp.model.NoDueRequest;
import com.example.neonapp.repository.NoDueRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
