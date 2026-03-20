package com.bloodbank.controller;

import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    @Autowired
    private DonorRepository donorRepo;

    // GET all donors
    @GetMapping
    public List<Donor> getAll() {
        return donorRepo.findAll();
    }

    // GET single donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getById(@PathVariable Integer id) {
        return donorRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET donors by blood group
    @GetMapping("/bloodgroup/{group}")
    public List<Donor> getByBloodGroup(@PathVariable String group) {
        return donorRepo.findByBloodGroup(group);
    }

    // GET available donors
    @GetMapping("/available")
    public List<Donor> getAvailable() {
        return donorRepo.findByAvailableToDonate(true);
    }

    // POST create new donor
    @PostMapping
    public Donor create(@RequestBody Donor donor) {
        return donorRepo.save(donor);
    }

    // PUT update donor
    @PutMapping("/{id}")
    public ResponseEntity<Donor> update(@PathVariable Integer id, @RequestBody Donor updated) {
        return donorRepo.findById(id).map(donor -> {
            donor.setFullName(updated.getFullName());
            donor.setGender(updated.getGender());
            donor.setAge(updated.getAge());
            donor.setBloodGroup(updated.getBloodGroup());
            donor.setPhone(updated.getPhone());
            donor.setEmail(updated.getEmail());
            donor.setCity(updated.getCity());
            donor.setAddress(updated.getAddress());
            donor.setAvailableToDonate(updated.getAvailableToDonate());
            donor.setLastDonationDate(updated.getLastDonationDate());
            return ResponseEntity.ok(donorRepo.save(donor));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE donor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!donorRepo.existsById(id)) return ResponseEntity.notFound().build();
        donorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
