package com.ummbatin.service_management.models;

import com.ummbatin.service_management.models.Child;
import com.ummbatin.service_management.models.Complaint;
import com.ummbatin.service_management.models.Payment;
import com.ummbatin.service_management.models.Property;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "residents")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private Long residentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "national_id", nullable = false, unique = true, length = 20)
    private String nationalId;

    @Column(name = "primary_residence_id")
    private Long primaryResidenceId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Property> properties;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Child> children;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Long getPrimaryResidenceId() {
        return primaryResidenceId;
    }

    public void setPrimaryResidenceId(Long primaryResidenceId) {
        this.primaryResidenceId = primaryResidenceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
