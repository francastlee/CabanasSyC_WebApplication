package com.castleedev.cabanassyc_backend.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "booking")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Date date;
    Double totalPrice;
    boolean state;
    
    @OneToMany(mappedBy = "booking")
    private List<CabinBooking> cabinBookingList;

    @OneToMany(mappedBy = "booking")
    private List<BookingTour> bookingTourList;
}
