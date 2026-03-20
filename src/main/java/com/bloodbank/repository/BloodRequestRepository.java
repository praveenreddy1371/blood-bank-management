package com.bloodbank.repository;

import com.bloodbank.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {
    List<BloodRequest> findByStatus(String status);
    List<BloodRequest> findByBloodGroup(String bloodGroup);
}
