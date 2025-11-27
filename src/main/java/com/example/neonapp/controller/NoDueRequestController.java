package com.example.neonapp.controller;

import com.example.neonapp.dto.ApproveNoDueRequestDto;
import com.example.neonapp.dto.CreateNoDueRequestDto;
import com.example.neonapp.dto.DeclineNoDueRequestDto;
import com.example.neonapp.model.NoDueRequest;
import com.example.neonapp.service.NoDueRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    // Get by subject
    @GetMapping("/by-subject")
    public ResponseEntity<List<NoDueRequest>> getDueRequestBySubject(@RequestParam String subject) {
        return ResponseEntity.ok(service.getBySubject(subject));
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

    // Approve (by id OR enrollment+subject)
    // Behavior change: if DTO contains id, we check current status first.
    @PostMapping("/approve")
    public ResponseEntity<?> approveNoDueRequest(@RequestBody ApproveNoDueRequestDto dto) {
        // if id provided -> do status check + approveById
        if (dto != null && dto.getId() != null) {
            Optional<NoDueRequest> opt = service.getById(dto.getId());
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NoDueRequest not found with id: " + dto.getId());
            }
            NoDueRequest existing = opt.get();
            String cur = existing.getStatus() == null ? "" : existing.getStatus().toUpperCase();

            if ("APPROVED".equals(cur)) {
                // already approved -> no-op
                return ResponseEntity.ok(existing);
            }
            if ("DECLINED".equals(cur)) {
                // cannot approve a declined request
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Request with id " + dto.getId() + " is already DECLINED and cannot be approved.");
            }
            // otherwise approve
            NoDueRequest updated = service.approveById(dto.getId(), dto.getApproverName());
            return ResponseEntity.ok(updated);
        }

        // fallback: original behaviour (by enrollment+subject or id absent)
        NoDueRequest updated = service.approve(dto);
        return ResponseEntity.ok(updated);
    }

    // Decline endpoint
    // Behavior change: if DTO contains id, check current status first.
    @PostMapping("/decline")
    public ResponseEntity<?> declineNoDueRequest(@RequestBody DeclineNoDueRequestDto dto) {
        if (dto == null || dto.getId() == null) {
            // require id for this path (keeps compatibility if you prefer otherwise)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is required to decline a request");
        }

        Optional<NoDueRequest> opt = service.getById(dto.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NoDueRequest not found with id: " + dto.getId());
        }

        NoDueRequest existing = opt.get();
        String cur = existing.getStatus() == null ? "" : existing.getStatus().toUpperCase();

        if ("DECLINED".equals(cur)) {
            // already declined -> no-op
            return ResponseEntity.ok(existing);
        }
        if ("APPROVED".equals(cur)) {
            // cannot decline an approved request
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Request with id " + dto.getId() + " is already APPROVED and cannot be declined.");
        }

        // perform decline: the service will set status and (if you added fields) reason/decliner/declinedAt
        NoDueRequest updated = service.decline(dto);
        return ResponseEntity.ok(updated);
    }
}
