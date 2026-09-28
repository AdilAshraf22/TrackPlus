package com.trackplus.trackplusbackend.repository;

import com.trackplus.trackplusbackend.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

    List<StatusHistory> findByShipmentIdOrderByTimestampAsc(Long shipmentId);
}