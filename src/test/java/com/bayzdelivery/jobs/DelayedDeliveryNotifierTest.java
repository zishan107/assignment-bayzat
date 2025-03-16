package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class DelayedDeliveryNotifierTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DelayedDeliveryNotifier delayedDeliveryNotifier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckDelayedDeliveries() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);
        delivery.setStartTime(Instant.now().minusSeconds(3600));

        List<Delivery> delayedDeliveries = Collections.singletonList(delivery);

        when(deliveryRepository.findDelayedDeliveries(any(Instant.class))).thenReturn(delayedDeliveries);

        delayedDeliveryNotifier.checkDelayedDeliveries();

        verify(deliveryRepository, times(1)).findDelayedDeliveries(any(Instant.class));
        verify(delayedDeliveryNotifier, times(1)).notifyCustomerSupport(delivery);
    }
}