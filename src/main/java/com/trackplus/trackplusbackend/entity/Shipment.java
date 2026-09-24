package com.trackplus.trackplusbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus currentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime estimatedDeliveryAt;

    private LocalDateTime actualDeliveryAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Shipment() {
    }

    public Shipment(String trackingCode, ShipmentStatus currentStatus,
                    LocalDateTime createdAt,
                    LocalDateTime estimatedDeliveryAt,
                    User customer,
                    User driver,
                    Route route) {
        this.trackingCode = trackingCode;
        this.currentStatus = currentStatus;
        this.createdAt = createdAt;
        this.estimatedDeliveryAt = estimatedDeliveryAt;
        this.customer = customer;
        this.driver = driver;
        this.route = route;
    }

    public Long getId() {
        return id;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public ShipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ShipmentStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEstimatedDeliveryAt() {
        return estimatedDeliveryAt;
    }

    public void setEstimatedDeliveryAt(LocalDateTime estimatedDeliveryAt) {
        this.estimatedDeliveryAt = estimatedDeliveryAt;
    }

    public LocalDateTime getActualDeliveryAt() {
        return actualDeliveryAt;
    }

    public void setActualDeliveryAt(LocalDateTime actualDeliveryAt) {
        this.actualDeliveryAt = actualDeliveryAt;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
