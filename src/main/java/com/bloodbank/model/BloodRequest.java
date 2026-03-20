package com.bloodbank.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "blood_request")
public class BloodRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id") private Integer requestId;
    @Column(name = "patient_name") private String patientName;
    @Column(name = "blood_group") private String bloodGroup;
    @Column(name = "units_required") private Integer unitsRequired;
    @Column(name = "request_date") private LocalDate requestDate;
    private String status, city;
    @Column(name = "hospital_id") private Integer hospitalId;
    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer v) { this.requestId = v; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String v) { this.patientName = v; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String v) { this.bloodGroup = v; }
    public Integer getUnitsRequired() { return unitsRequired; }
    public void setUnitsRequired(Integer v) { this.unitsRequired = v; }
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate v) { this.requestDate = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }
    public Integer getHospitalId() { return hospitalId; }
    public void setHospitalId(Integer v) { this.hospitalId = v; }
}