package com.bloodbank.model;
import jakarta.persistence.*;
@Entity
@Table(name = "admin")
public class Admin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id") private Integer adminId;
    private String name;
    private String email;
    private String password;
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer v) { this.adminId = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
}
