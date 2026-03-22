package com.bloodbank.controller;
import com.bloodbank.model.Admin;
import com.bloodbank.model.Donor;
import com.bloodbank.model.Hospital;
import com.bloodbank.repository.AdminRepository;
import com.bloodbank.repository.DonorRepository;
import com.bloodbank.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AdminRepository adminRepo;
    @Autowired private DonorRepository donorRepo;
    @Autowired private HospitalRepository hospitalRepo;

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> body) {
        Optional<Admin> admin = adminRepo.findByEmailAndPassword(body.get("email"), body.get("password"));
        if (admin.isPresent()) return ResponseEntity.ok(admin.get());
        return ResponseEntity.status(401).body("Invalid admin credentials");
    }

    @PostMapping("/donor/login")
    public ResponseEntity<?> donorLogin(@RequestBody Map<String, String> body) {
        Optional<Donor> donor = donorRepo.findByEmailAndPhone(body.get("email"), body.get("password"));
        if (donor.isPresent()) return ResponseEntity.ok(donor.get());
        return ResponseEntity.status(401).body("Invalid donor credentials");
    }

    @PostMapping("/hospital/login")
    public ResponseEntity<?> hospitalLogin(@RequestBody Map<String, String> body) {
        Optional<Hospital> hospital = hospitalRepo.findByEmailAndPhone(body.get("email"), body.get("password"));
        if (hospital.isPresent()) return ResponseEntity.ok(hospital.get());
        return ResponseEntity.status(401).body("Invalid hospital credentials");
    }
}