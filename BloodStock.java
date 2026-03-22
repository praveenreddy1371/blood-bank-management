package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "blood_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "units_available")
    private Integer unitsAvailable;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "hospital_id")
    private Integer hospitalId;

    @Transient
    private String hospitalName;
}
