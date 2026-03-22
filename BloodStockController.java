package com.bloodbank.controller;

import com.bloodbank.model.BloodStock;
import com.bloodbank.repository.BloodStockRepository;
import com.bloodbank.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class BloodStockController {

    @Autowired
    private BloodStockRepository stockRepo;

    @Autowired
    private HospitalRepository hospitalRepo;

    // Enrich with hospital name
    private List<BloodStock> enrich(List<BloodStock> stocks) {
        stocks.forEach(s ->
            hospitalRepo.findById(s.getHospitalId())
                    .ifPresent(h -> s.setHospitalName(h.getHospitalName()))
        );
        return stocks;
    }

    // GET all stock
    @GetMapping
    public List<BloodStock> getAll() {
        return enrich(stockRepo.findAll());
    }

    // GET stock by hospital
    @GetMapping("/hospital/{hospitalId}")
    public List<BloodStock> getByHospital(@PathVariable Integer hospitalId) {
        return enrich(stockRepo.findByHospitalId(hospitalId));
    }

    // POST add or update stock entry
    // If hospital+bloodGroup combo already exists, update units; otherwise create new
    @PostMapping
    public BloodStock createOrUpdate(@RequestBody BloodStock incoming) {
        incoming.setLastUpdated(LocalDate.now());

        return stockRepo
                .findByHospitalIdAndBloodGroup(incoming.getHospitalId(), incoming.getBloodGroup())
                .map(existing -> {
                    existing.setUnitsAvailable(incoming.getUnitsAvailable());
                    existing.setLastUpdated(LocalDate.now());
                    return stockRepo.save(existing);
                })
                .orElseGet(() -> stockRepo.save(incoming));
    }

    // DELETE stock entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!stockRepo.existsById(id)) return ResponseEntity.notFound().build();
        stockRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
