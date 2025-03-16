package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DelayedDeliveryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);

    private final DeliveryRepository deliveryRepository;

    // Injecting DeliveryRepository
    public DelayedDeliveryNotifier(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * Task 3
     * Customer support team wants to be notified when a delivery is not done in 45 minutes.
     * Create the scheduled task to check and notify CS team asynchronously.
     * Implementing notification not required, just print the message.
     */
    @Scheduled(fixedDelay = 30000) // Run every 30 seconds
    public void checkDelayedDeliveries() {
        Instant cutoffTime = Instant.now().minus(45, ChronoUnit.MINUTES);
        List<Delivery> delayedDeliveries = deliveryRepository.findDelayedDeliveries(cutoffTime);

        delayedDeliveries.forEach(this::notifyCustomerSupport);
    }

    /**
     * This method should be called to notify customer support team
     * It just writes notification on console but it may be email or push notification in real.
     * So that this method runs asynchronously.
     */
    @Async
    public void notifyCustomerSupport(Delivery delivery) {
        LOG.info("Delivery {} is delayed. Notify customer support.", delivery.getId());
    }
}
