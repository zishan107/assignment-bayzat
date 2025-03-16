package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import java.time.Instant;
import java.util.List;

@RestResource(exported = false)
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT d FROM Delivery d WHERE d.startTime BETWEEN :startTime AND :endTime")
    List<Delivery> findByStartTimeBetween(Instant startTime, Instant endTime);

    @Query("SELECT d FROM Delivery d WHERE d.endTime IS NULL AND d.startTime < :cutoffTime ORDER BY d.startTime ASC")
    List<Delivery> findDelayedDeliveries(Instant cutoffTime);
}
