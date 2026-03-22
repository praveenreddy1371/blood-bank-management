package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "blood_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "units_required")
    private Integer unitsRequired;

    @Column(name = "request_date")
    private LocalDate requestDate;

    private String status;
    private String city;

    @Column(name = "hospital_id")
    private Integer hospitalId;
}
