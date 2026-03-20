package com.bloodbank.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "donor")
public class Donor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id") private Integer donorId;
    @Column(name = "full_name") private String fullName;
    private String gender;
    private Integer age;
    @Column(name = "blood_group") private String bloodGroup;
    private String phone, email, city, address;
    @Column(name = "last_donation_date") private LocalDate lastDonationDate;
    @Column(name = "available_to_donate") private Boolean availableToDonate;
    public Integer getDonorId() { return donorId; }
    public void setDonorId(Integer v) { this.donorId = v; }
    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }
    public String getGender() { return gender; }
    public void setGender(String v) { this.gender = v; }
    public Integer getAge() { return age; }
    public void setAge(Integer v) { this.age = v; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String v) { this.bloodGroup = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }
    public String getAddress() { return address; }
    public void setAddress(String v) { this.address = v; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDate v) { this.lastDonationDate = v; }
    public Boolean getAvailableToDonate() { return availableToDonate; }
    public void setAvailableToDonate(Boolean v) { this.availableToDonate = v; }
}