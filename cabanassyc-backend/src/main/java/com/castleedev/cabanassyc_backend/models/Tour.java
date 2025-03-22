package com.castleedev.cabanassyc_backend.Models;

import java.sql.Time;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tour")
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourId")
    private Long id;

    private String name;
    private int capacity;
    private Double price;
    private Time startTime;
    private Time endTime;
    boolean state;

    @OneToMany(mappedBy = "tour")
    private List<BookingTour> bookingTourList;

    public Tour(Long id, String name, int capacity, Double price, Time startTime, Time endTime, boolean state) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
    }

}
