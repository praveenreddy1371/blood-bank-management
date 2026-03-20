package com.bloodbank.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "blood_stock")
public class BloodStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id") private Integer stockId;
    @Column(name = "blood_group") private String bloodGroup;
    @Column(name = "units_available") private Integer unitsAvailable;
    @Column(name = "last_updated") private LocalDate lastUpdated;
    @Column(name = "hospital_id") private Integer hospitalId;
    @Transient private String hospitalName;
    public Integer getStockId() { return stockId; }
    public void setStockId(Integer v) { this.stockId = v; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String v) { this.bloodGroup = v; }
    public Integer getUnitsAvailable() { return unitsAvailable; }
    public void setUnitsAvailable(Integer v) { this.unitsAvailable = v; }
    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate v) { this.lastUpdated = v; }
    public Integer getHospitalId() { return hospitalId; }
    public void setHospitalId(Integer v) { this.hospitalId = v; }
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String v) { this.hospitalName = v; }
}