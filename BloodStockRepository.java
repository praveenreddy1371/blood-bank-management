package com.bloodbank.repository;

import com.bloodbank.model.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BloodStockRepository extends JpaRepository<BloodStock, Integer> {
    List<BloodStock> findByHospitalId(Integer hospitalId);
    Optional<BloodStock> findByHospitalIdAndBloodGroup(Integer hospitalId, String bloodGroup);
}
