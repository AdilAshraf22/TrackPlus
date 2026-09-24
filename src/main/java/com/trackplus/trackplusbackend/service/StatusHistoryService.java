package com.trackplus.trackplusbackend.service;

import com.trackplus.trackplusbackend.dto.StatusUpdateRequest;
import com.trackplus.trackplusbackend.entity.Shipment;
import com.trackplus.trackplusbackend.entity.StatusHistory;
import com.trackplus.trackplusbackend.repository.ShipmentRepository;
import com.trackplus.trackplusbackend.repository.StatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusHistoryService {

    private final ShipmentRepository shipmentRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final ShipmentService shipmentService;

    public StatusHistoryService(ShipmentRepository shipmentRepository,
                                StatusHistoryRepository statusHistoryRepository,
                                ShipmentService shipmentService) {
        this.shipmentRepository = shipmentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.shipmentService = shipmentService;
    }

    public StatusHistory updateShipmentStatus(
            Long shipmentId,
            StatusUpdateRequest request) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        shipment.setCurrentStatus(request.getStatus());

        // Recalculate ETA after status change
        shipment.setEstimatedDeliveryAt(
                shipmentService.calculateEta(
                        shipment,
                        request.getStatus()
                )
        );

        // If delivered, record actual delivery time
        if (request.getStatus().name().equals("DELIVERED")) {
            shipment.setActualDeliveryAt(LocalDateTime.now());
        }

        shipmentRepository.save(shipment);

        StatusHistory history = new StatusHistory();

        history.setStatus(request.getStatus().name());
        history.setTimestamp(LocalDateTime.now());
        history.setNote(request.getNote());
        history.setShipment(shipment);

        return statusHistoryRepository.save(history);
    }

    public List<StatusHistory> getShipmentHistory(Long shipmentId) {
        return statusHistoryRepository
                .findByShipmentIdOrderByTimestampAsc(shipmentId);
    }
}
