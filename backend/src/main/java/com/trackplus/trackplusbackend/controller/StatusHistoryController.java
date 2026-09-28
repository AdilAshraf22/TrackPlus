package com.trackplus.trackplusbackend.controller;

import com.trackplus.trackplusbackend.dto.StatusUpdateRequest;
import com.trackplus.trackplusbackend.entity.StatusHistory;
import com.trackplus.trackplusbackend.service.StatusHistoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    public StatusHistoryController(StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @PutMapping("/{shipmentId}/status")
    public StatusHistory updateStatus(
            @PathVariable Long shipmentId,
            @Valid @RequestBody StatusUpdateRequest request) {

        return statusHistoryService.updateShipmentStatus(
                shipmentId,
                request
        );
    }

    @GetMapping("/{shipmentId}/history")
    public List<StatusHistory> getHistory(
            @PathVariable Long shipmentId) {

        return statusHistoryService.getShipmentHistory(shipmentId);
    }
}
