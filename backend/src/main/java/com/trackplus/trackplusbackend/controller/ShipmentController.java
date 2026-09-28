package com.trackplus.trackplusbackend.controller;

import com.trackplus.trackplusbackend.dto.PublicShipmentResponse;
import com.trackplus.trackplusbackend.dto.ShipmentRequest;
import com.trackplus.trackplusbackend.entity.Shipment;
import com.trackplus.trackplusbackend.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public Shipment createShipment(
            @Valid @RequestBody ShipmentRequest request) {

        return shipmentService.createShipment(request);
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    @GetMapping("/track/{trackingCode}")
    public PublicShipmentResponse getShipmentByTrackingCode(
            @PathVariable String trackingCode) {

        Shipment shipment =
                shipmentService.getShipmentByTrackingCode(trackingCode);

        return new PublicShipmentResponse(
                shipment.getTrackingCode(),
                shipment.getCurrentStatus(),
                shipment.getCreatedAt(),
                shipment.getEstimatedDeliveryAt(),
                shipment.getRoute().getOrigin(),
                shipment.getRoute().getDestination(),
                shipment.getActualDeliveryAt()
        );
    }
}
