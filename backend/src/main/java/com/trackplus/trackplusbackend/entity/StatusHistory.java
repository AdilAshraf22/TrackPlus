package com.trackplus.trackplusbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime timestamp;

    private String note;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    public StatusHistory() {
    }

    public StatusHistory(String status, LocalDateTime timestamp,
                         String note, Shipment shipment) {
        this.status = status;
        this.timestamp = timestamp;
        this.note = note;
        this.shipment = shipment;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
