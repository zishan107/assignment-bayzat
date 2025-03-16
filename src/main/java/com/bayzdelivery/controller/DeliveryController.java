package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bayzdelivery.service.DeliveryService;

@RestController
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @Autowired
  private DeliveryRepository deliveryRepository;

  @PostMapping(path ="/delivery")
  public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
    return ResponseEntity.ok(deliveryService.save(delivery));
  }

  @GetMapping(path = "/delivery/{deliveryId}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable(name = "deliveryId", required = true) Long deliveryId) {
      Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);
      if (delivery != null)
          return ResponseEntity.ok(delivery);
      return ResponseEntity.notFound().build();
  }

  @GetMapping("/delivery/top-delivery-men")
  public ResponseEntity<Map<String, Object>> getTopDeliveryMen(
          @RequestParam Instant startTime,
          @RequestParam Instant endTime) {

      List<Delivery> deliveries = deliveryRepository.findByStartTimeBetween(startTime, endTime);

      Map<Person, Long> commissionByDeliveryMan = deliveries.stream()
              .collect(Collectors.groupingBy(Delivery::getDeliveryMan, Collectors.summingLong(Delivery::getCommission)));

      List<Map.Entry<Person, Long>> topThreeDeliveryMen = commissionByDeliveryMan.entrySet().stream()
              .sorted(Map.Entry.<Person, Long>comparingByValue().reversed())
              .limit(3)
              .collect(Collectors.toList());

      double averageCommission = deliveries.stream()
              .mapToLong(Delivery::getCommission)
              .average()
              .orElse(0.0);

      Map<String, Object> response = Map.of(
              "topThreeDeliveryMen", topThreeDeliveryMen,
              "averageCommission", averageCommission
      );

      return ResponseEntity.ok(response);
  }
}
