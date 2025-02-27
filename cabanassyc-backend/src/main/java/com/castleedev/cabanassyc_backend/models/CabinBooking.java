package com.castleedev.cabanassyc_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cabinbooking")
public class CabinBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinBookingId")
    private Long cabinBookingId;

    @ManyToOne
    @JoinColumn(name = "cabinId")
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;

    private int adultsQuantity;
    private int childrenQuantity;
    private boolean state;
}
