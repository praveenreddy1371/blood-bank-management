package com.bloodbank.controller;

import com.bloodbank.model.Hospital;
import com.bloodbank.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepo;

    // GET all hospitals
    @GetMapping
    public List<Hospital> getAll() {
        return hospitalRepo.findAll();
    }

    // GET hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(@PathVariable Integer id) {
        return hospitalRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create hospital
    @PostMapping
    public Hospital create(@RequestBody Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    // PUT update hospital
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(@PathVariable Integer id, @RequestBody Hospital updated) {
        return hospitalRepo.findById(id).map(h -> {
            h.setHospitalName(updated.getHospitalName());
            h.setCity(updated.getCity());
            h.setPhone(updated.getPhone());
            h.setEmail(updated.getEmail());
            h.setAddress(updated.getAddress());
            return ResponseEntity.ok(hospitalRepo.save(h));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE hospital
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!hospitalRepo.existsById(id)) return ResponseEntity.notFound().build();
        hospitalRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
