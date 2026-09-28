package com.trackplus.trackplusbackend.service;

import com.trackplus.trackplusbackend.dto.ShipmentRequest;
import com.trackplus.trackplusbackend.entity.Route;
import com.trackplus.trackplusbackend.entity.Shipment;
import com.trackplus.trackplusbackend.entity.ShipmentStatus;
import com.trackplus.trackplusbackend.entity.User;
import com.trackplus.trackplusbackend.repository.RouteRepository;
import com.trackplus.trackplusbackend.repository.ShipmentRepository;
import com.trackplus.trackplusbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public ShipmentService(ShipmentRepository shipmentRepository,
                           UserRepository userRepository,
                           RouteRepository routeRepository) {
        this.shipmentRepository = shipmentRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    public Shipment createShipment(ShipmentRequest request) {

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User driver = userRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Shipment shipment = new Shipment();

        shipment.setTrackingCode(request.getTrackingCode());
        shipment.setCustomer(customer);
        shipment.setDriver(driver);
        shipment.setRoute(route);

        shipment.setCurrentStatus(ShipmentStatus.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());

        // Calculate initial ETA
        shipment.setEstimatedDeliveryAt(
                calculateEta(shipment, ShipmentStatus.CREATED)
        );

        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentByTrackingCode(String trackingCode) {
        return shipmentRepository.findByTrackingCode(trackingCode)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public LocalDateTime calculateEta(Shipment shipment, ShipmentStatus status) {

        Route route = shipment.getRoute();

        double progress = getProgress(status);

        double remainingDistance =
                route.getDistanceKm() * (1 - progress);

        double congestionMultiplier = 1.0;

        if (status == ShipmentStatus.IN_TRANSIT) {
            congestionMultiplier = 1.2;
        }

        double remainingHours =
                (remainingDistance / route.getAvgSpeedKmph())
                        * congestionMultiplier;

        return LocalDateTime.now()
                .plusMinutes((long) (remainingHours * 60));
    }

    private double getProgress(ShipmentStatus status) {

        return switch (status) {
            case CREATED -> 0.0;
            case PICKED_UP -> 0.10;
            case IN_TRANSIT -> 0.50;
            case OUT_FOR_DELIVERY -> 0.80;
            case DELIVERED -> 1.0;
            case DELAYED -> 0.50;
        };
    }
}
