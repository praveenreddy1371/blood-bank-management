// ─── DonorRepository.java ────────────────────────────────────
package com.bloodbank.repository;

import com.bloodbank.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Integer> {
    List<Donor> findByBloodGroup(String bloodGroup);
    List<Donor> findByAvailableToDonate(Boolean available);
    List<Donor> findByCity(String city);
}
