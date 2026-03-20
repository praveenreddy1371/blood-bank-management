package com.bloodbank.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@Entity
@Table(name = "donation_records")
public class DonationRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id") private Integer donationId;
    @Column(name = "donor_id") private Integer donorId;
    @Column(name = "hospital_id") private Integer hospitalId;
    @Column(name = "donation_date") private LocalDate donationDate;
    @Column(name = "units_donated") private Integer unitsDonated;
    @Transient private String donorName;
    @Transient private String hospitalName;
    public Integer getDonationId() { return donationId; }
    public void setDonationId(Integer v) { this.donationId = v; }
    public Integer getDonorId() { return donorId; }
    public void setDonorId(Integer v) { this.donorId = v; }
    public Integer getHospitalId() { return hospitalId; }
    public void setHospitalId(Integer v) { this.hospitalId = v; }
    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate v) { this.donationDate = v; }
    public Integer getUnitsDonated() { return unitsDonated; }
    public void setUnitsDonated(Integer v) { this.unitsDonated = v; }
    public String getDonorName() { return donorName; }
    public void setDonorName(String v) { this.donorName = v; }
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String v) { this.hospitalName = v; }
}