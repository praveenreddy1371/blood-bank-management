package com.bloodbank.repository;

import com.bloodbank.model.DonationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRecordRepository extends JpaRepository<DonationRecord, Integer> {
    List<DonationRecord> findByDonorId(Integer donorId);
    List<DonationRecord> findByHospitalId(Integer hospitalId);
}
