package com.bloodbank.controller;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestRepository requestRepo;

    // GET all requests
    @GetMapping
    public List<BloodRequest> getAll() {
        return requestRepo.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest> getById(@PathVariable Integer id) {
        return requestRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET by status (Pending / Approved / Rejected)
    @GetMapping("/status/{status}")
    public List<BloodRequest> getByStatus(@PathVariable String status) {
        return requestRepo.findByStatus(status);
    }

    // GET by blood group
    @GetMapping("/bloodgroup/{group}")
    public List<BloodRequest> getByBloodGroup(@PathVariable String group) {
        return requestRepo.findByBloodGroup(group);
    }

    // POST create new request
    @PostMapping
    public BloodRequest create(@RequestBody BloodRequest request) {
        if (request.getStatus() == null) request.setStatus("Pending");
        return requestRepo.save(request);
    }

    // PATCH update status only (Approve / Reject)
    @PatchMapping("/{id}/status")
    public ResponseEntity<BloodRequest> updateStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        return requestRepo.findById(id).map(req -> {
            req.setStatus(status);
            return ResponseEntity.ok(requestRepo.save(req));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PUT full update
    @PutMapping("/{id}")
    public ResponseEntity<BloodRequest> update(@PathVariable Integer id, @RequestBody BloodRequest updated) {
        return requestRepo.findById(id).map(req -> {
            req.setPatientName(updated.getPatientName());
            req.setBloodGroup(updated.getBloodGroup());
            req.setUnitsRequired(updated.getUnitsRequired());
            req.setRequestDate(updated.getRequestDate());
            req.setStatus(updated.getStatus());
            req.setCity(updated.getCity());
            req.setHospitalId(updated.getHospitalId());
            return ResponseEntity.ok(requestRepo.save(req));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!requestRepo.existsById(id)) return ResponseEntity.notFound().build();
        requestRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
