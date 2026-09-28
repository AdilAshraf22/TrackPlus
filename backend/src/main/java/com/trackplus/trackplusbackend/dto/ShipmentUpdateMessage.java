package com.trackplus.trackplusbackend.dto;

import com.trackplus.trackplusbackend.entity.ShipmentStatus;

import java.time.LocalDateTime;

public class ShipmentUpdateMessage {

    private Long shipmentId;
    private String trackingCode;
    private ShipmentStatus status;
    private LocalDateTime estimatedDeliveryAt;
    private String note;

    public ShipmentUpdateMessage() {
    }

    public ShipmentUpdateMessage(
            Long shipmentId,
            String trackingCode,
            ShipmentStatus status,
            LocalDateTime estimatedDeliveryAt,
            String note) {

        this.shipmentId = shipmentId;
        this.trackingCode = trackingCode;
        this.status = status;
        this.estimatedDeliveryAt = estimatedDeliveryAt;
        this.note = note;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedDeliveryAt() {
        return estimatedDeliveryAt;
    }

    public void setEstimatedDeliveryAt(
            LocalDateTime estimatedDeliveryAt) {
        this.estimatedDeliveryAt = estimatedDeliveryAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}