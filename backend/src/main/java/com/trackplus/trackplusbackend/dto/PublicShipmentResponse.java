package com.trackplus.trackplusbackend.dto;

import com.trackplus.trackplusbackend.entity.ShipmentStatus;

import java.time.LocalDateTime;

public class PublicShipmentResponse {

    private String trackingCode;
    private ShipmentStatus currentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime estimatedDeliveryAt;

    private String origin;
    private String destination;

    private LocalDateTime actualDeliveryAt;

    public PublicShipmentResponse() {
    }

    public PublicShipmentResponse(
            String trackingCode,
            ShipmentStatus currentStatus,
            LocalDateTime createdAt,
            LocalDateTime estimatedDeliveryAt,
            String origin,
            String destination,
            LocalDateTime actualDeliveryAt) {

        this.trackingCode = trackingCode;
        this.currentStatus = currentStatus;
        this.createdAt = createdAt;
        this.estimatedDeliveryAt = estimatedDeliveryAt;
        this.origin = origin;
        this.destination = destination;
        this.actualDeliveryAt = actualDeliveryAt;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public ShipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getEstimatedDeliveryAt() {
        return estimatedDeliveryAt;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getActualDeliveryAt() {
        return actualDeliveryAt;
    }
}
