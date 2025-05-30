package com.ummbatin.service_management.models;

import com.ummbatin.service_management.models.Payment;
import com.ummbatin.service_management.models.Resident;
import com.ummbatin.service_management.models.WaterReading;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Integer propertyId;

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private BigDecimal area;

    @Column(name = "number_of_units", nullable = false)
    private Integer numberOfUnits;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<WaterReading> waterReadings;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public List<WaterReading> getWaterReadings() {
        return waterReadings;
    }

    public void setWaterReadings(List<WaterReading> waterReadings) {
        this.waterReadings = waterReadings;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
