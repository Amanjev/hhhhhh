package com.example.neonapp.controller;

import com.example.neonapp.dto.ApproveNoDueRequestDto;
import com.example.neonapp.dto.CreateNoDueRequestDto;
import com.example.neonapp.model.NoDueRequest;
import com.example.neonapp.service.NoDueRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/no-due-requests")
public class NoDueRequestController {

    private final NoDueRequestService service;

    public NoDueRequestController(NoDueRequestService service) {
        this.service = service;
    }

    // Create
    @PostMapping
    public ResponseEntity<NoDueRequest> create(@RequestBody CreateNoDueRequestDto dto) {
        NoDueRequest saved = service.createRequest(dto);
        return ResponseEntity.created(URI.create("/api/no-due-requests/" + saved.getId())).body(saved);
    }

    // List all
    @GetMapping
    public ResponseEntity<List<NoDueRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Get by enrollment
    @GetMapping("/by-enrollment")
    public ResponseEntity<List<NoDueRequest>> getByEnrollment(@RequestParam("enrollment") String enrollment) {
        return ResponseEntity.ok(service.getByEnrollment(enrollment));
    }

    // Get single
    @GetMapping("/{id}")
    public ResponseEntity<NoDueRequest> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Approve by id (simple endpoint)
    @PatchMapping("/{id}/approve")
    public ResponseEntity<NoDueRequest> approveById(@PathVariable Long id) {
        NoDueRequest updated = service.approveById(id, null);
        return ResponseEntity.ok(updated);
    }

    // Reject by id (simple endpoint)
    @PatchMapping("/{id}/reject")
    public ResponseEntity<NoDueRequest> rejectById(@PathVariable Long id) {
        NoDueRequest updated = service.updateStatus(id, "REJECTED").orElseThrow(() -> 
            new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(updated);
    }

    // -----------------------------
    // NEW: Approve endpoint (by id OR by enrollment + subject)
    // -----------------------------
    @PostMapping("/approve")
    public ResponseEntity<NoDueRequest> approveNoDueRequest(@RequestBody ApproveNoDueRequestDto dto) {
        NoDueRequest updated = service.approve(dto);
        return ResponseEntity.ok(updated);
    }
}
