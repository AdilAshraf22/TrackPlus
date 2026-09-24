package com.trackplus.trackplusbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class RouteRequest {

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @Positive(message = "Distance must be greater than 0")
    private double distanceKm;

    @Positive(message = "Average speed must be greater than 0")
    private double avgSpeedKmph;

    public RouteRequest() {}

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getAvgSpeedKmph() {
        return avgSpeedKmph;
    }

    public void setAvgSpeedKmph(double avgSpeedKmph) {
        this.avgSpeedKmph = avgSpeedKmph;
    }
}