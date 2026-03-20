package com.bloodbank.controller;

import com.bloodbank.model.DonationRecord;
import com.bloodbank.repository.DonationRecordRepository;
import com.bloodbank.repository.DonorRepository;
import com.bloodbank.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationRecordRepository donationRepo;

    @Autowired
    private DonorRepository donorRepo;

    @Autowired
    private HospitalRepository hospitalRepo;

    // Enrich records with donor and hospital names for display
    private List<DonationRecord> enrich(List<DonationRecord> records) {
        records.forEach(d -> {
            donorRepo.findById(d.getDonorId())
                    .ifPresent(donor -> d.setDonorName(donor.getFullName()));
            hospitalRepo.findById(d.getHospitalId())
                    .ifPresent(h -> d.setHospitalName(h.getHospitalName()));
        });
        return records;
    }

    // GET all donation records
    @GetMapping
    public List<DonationRecord> getAll() {
        return enrich(donationRepo.findAll());
    }

    // GET donations by donor ID
    @GetMapping("/donor/{donorId}")
    public List<DonationRecord> getByDonor(@PathVariable Integer donorId) {
        return enrich(donationRepo.findByDonorId(donorId));
    }

    // GET donations by hospital ID
    @GetMapping("/hospital/{hospitalId}")
    public List<DonationRecord> getByHospital(@PathVariable Integer hospitalId) {
        return enrich(donationRepo.findByHospitalId(hospitalId));
    }

    // POST record a new donation
    @PostMapping
    public DonationRecord create(@RequestBody DonationRecord record) {
        return donationRepo.save(record);
    }

    // DELETE donation record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!donationRepo.existsById(id)) return ResponseEntity.notFound().build();
        donationRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
