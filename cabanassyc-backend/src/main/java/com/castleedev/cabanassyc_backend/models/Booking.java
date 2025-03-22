package com.castleedev.cabanassyc_backend.Models;

import java.time.LocalDate;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDate date;
    Double totalPrice;
    boolean state;
    
    @OneToMany(mappedBy = "booking")
    private List<CabinBooking> cabinBookingList;

    @OneToMany(mappedBy = "booking")
    private List<BookingTour> bookingTourList;

    public Booking(Long id, User user, LocalDate date, Double totalPrice, boolean state) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.totalPrice = totalPrice;
        this.state = state;
    }

}
