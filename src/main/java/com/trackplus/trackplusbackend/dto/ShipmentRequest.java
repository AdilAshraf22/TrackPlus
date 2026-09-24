package com.trackplus.trackplusbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShipmentRequest {

    @NotBlank(message = "Tracking code is required")
    private String trackingCode;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Driver ID is required")
    private Long driverId;

    @NotNull(message = "Route ID is required")
    private Long routeId;

    public ShipmentRequest() {}

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
