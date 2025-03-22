package com.castleedev.cabanassyc_backend.Models;

import java.time.LocalDate;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "workingday")
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workingDayId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDate date;
    private Time checkInTime;
    private Time checkOutTime;
    private boolean state;
    
}
