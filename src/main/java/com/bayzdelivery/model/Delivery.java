package com.bayzdelivery.model;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 123765351514001L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "start_time")
    Instant startTime;

    @NotNull
    @Column(name = "end_time")
    Instant endTime;

    @Column(name = "distance")
    Long distance;

    @Column(name = "price")
    Long price;

    @Column(name = "commission")
    Long commission;

    @ManyToOne
    @JoinColumn(name = "delivery_man_id", referencedColumnName = "id")
    Person deliveryMan;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Person customer;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Person getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(Person deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }
}