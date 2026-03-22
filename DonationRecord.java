package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "donation_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Integer donationId;

    @Column(name = "donor_id")
    private Integer donorId;

    @Column(name = "hospital_id")
    private Integer hospitalId;

    @Column(name = "donation_date")
    private LocalDate donationDate;

    @Column(name = "units_donated")
    private Integer unitsDonated;

    // These are populated via join queries
    @Transient
    private String donorName;

    @Transient
    private String hospitalName;
}
