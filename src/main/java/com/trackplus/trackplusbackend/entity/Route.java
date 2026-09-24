package com.trackplus.trackplusbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;

    private String destination;

    private double distanceKm;

    private double avgSpeedKmph;

    public Route() {
    }

    public Route(String origin, String destination, double distanceKm, double avgSpeedKmph) {
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
        this.avgSpeedKmph = avgSpeedKmph;
    }

    public Long getId() {
        return id;
    }

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
