package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.DeliveryService;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeliveryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryController deliveryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build();
    }

    @Test
    public void testCreateNewDelivery() throws Exception {
        Delivery delivery = new Delivery();
        delivery.setId(1L);
        delivery.setStartTime(Instant.now());
        delivery.setEndTime(Instant.now().plusSeconds(3600));
        delivery.setDistance(10L);
        delivery.setPrice(100L);
        delivery.setCommission(15L);

        when(deliveryService.save(any(Delivery.class))).thenReturn(delivery);

        mockMvc.perform(post("/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startTime\": \"2023-01-01T00:00:00Z\", \"endTime\": \"2023-01-01T01:00:00Z\", \"distance\": 10, \"price\": 100, \"commission\": 15}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"startTime\": \"2023-01-01T00:00:00Z\", \"endTime\": \"2023-01-01T01:00:00Z\", \"distance\": 10, \"price\": 100, \"commission\": 15}"));
    }

    @Test
    public void testGetTopDeliveryMen() throws Exception {
        Person deliveryMan1 = new Person();
        deliveryMan1.setName("John Doe");
        deliveryMan1.setEmail("john.doe@example.com");
        deliveryMan1.setRole("delivery_man");

        Person deliveryMan2 = new Person();
        deliveryMan2.setName("Jane Doe");
        deliveryMan2.setEmail("jane.doe@example.com");
        deliveryMan2.setRole("delivery_man");

        Delivery delivery1 = new Delivery();
        delivery1.setDeliveryMan(deliveryMan1);
        delivery1.setCommission(50L);

        Delivery delivery2 = new Delivery();
        delivery2.setDeliveryMan(deliveryMan2);
        delivery2.setCommission(30L);

        List<Delivery> deliveries = Arrays.asList(delivery1, delivery2);

        when(deliveryRepository.findByStartTimeBetween(any(Instant.class), any(Instant.class))).thenReturn(deliveries);

        mockMvc.perform(get("/delivery/top-delivery-men")
                .param("startTime", "2023-01-01T00:00:00Z")
                .param("endTime", "2023-12-31T23:59:59Z"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"topThreeDeliveryMen\": [{\"key\": {\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"role\": \"delivery_man\"}, \"value\": 50}, {\"key\": {\"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\", \"role\": \"delivery_man\"}, \"value\": 30}], \"averageCommission\": 40.0}"));
    }
}