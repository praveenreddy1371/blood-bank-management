package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "donor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Integer donorId;

    @Column(name = "full_name")
    private String fullName;

    private String gender;
    private Integer age;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String phone;
    private String email;
    private String city;
    private String address;

    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    @Column(name = "available_to_donate")
    private Boolean availableToDonate;
}
