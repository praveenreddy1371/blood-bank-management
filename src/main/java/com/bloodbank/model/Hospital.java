package com.bloodbank.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "hospital")
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id") private Integer hospitalId;
    @Column(name = "hospital_name") private String hospitalName;
    private String city, phone, email, address;
    public Integer getHospitalId() { return hospitalId; }
    public void setHospitalId(Integer v) { this.hospitalId = v; }
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String v) { this.hospitalName = v; }
    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getAddress() { return address; }
    public void setAddress(String v) { this.address = v; }
}