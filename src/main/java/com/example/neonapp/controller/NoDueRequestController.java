package com.example.neonapp.controller;

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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateNoDueRequestDto dto) {
        NoDueRequest saved = service.createRequest(dto);
        return ResponseEntity.created(URI.create("/api/no-due-requests/" + saved.getId()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<NoDueRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-enrollment")
    public ResponseEntity<List<NoDueRequest>> getByEnrollment(@RequestParam String enrollment) {
        return ResponseEntity.ok(service.getByEnrollment(enrollment));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        return service.updateStatus(id, "APPROVED")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        return service.updateStatus(id, "REJECTED")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
