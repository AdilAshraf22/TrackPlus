package com.trackplus.trackplusbackend.service;

import com.trackplus.trackplusbackend.dto.ShipmentUpdateMessage;
import com.trackplus.trackplusbackend.entity.Shipment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShipmentWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public ShipmentWebSocketService(
            SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastShipmentUpdate(
            Shipment shipment,
            String note) {

        ShipmentUpdateMessage message =
                new ShipmentUpdateMessage(
                        shipment.getId(),
                        shipment.getTrackingCode(),
                        shipment.getCurrentStatus(),
                        shipment.getEstimatedDeliveryAt(),
                        note
                );

        String destination =
                "/topic/shipment/" + shipment.getId();

        messagingTemplate.convertAndSend(
                destination,
                message
        );
    }
}