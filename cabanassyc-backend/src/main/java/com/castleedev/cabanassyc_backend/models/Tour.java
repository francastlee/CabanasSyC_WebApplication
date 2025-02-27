package com.castleedev.cabanassyc_backend.models;

import java.sql.Time;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tour")
public class Tour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tourId;

    private String name;
    private int capacity;
    private Double price;
    private Time startTime;
    private Time endTime;
    boolean state;

    @OneToMany(mappedBy = "tour")
    private List<BookingTour> bookingTourList;
}
