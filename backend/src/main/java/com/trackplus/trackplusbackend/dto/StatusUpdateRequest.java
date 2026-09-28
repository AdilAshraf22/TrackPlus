package com.trackplus.trackplusbackend.dto;

import com.trackplus.trackplusbackend.entity.ShipmentStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ShipmentStatus status;

    private String note;

    public StatusUpdateRequest() {}

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
