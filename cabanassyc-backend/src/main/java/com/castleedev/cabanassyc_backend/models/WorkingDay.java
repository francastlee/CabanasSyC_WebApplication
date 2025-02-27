package com.castleedev.cabanassyc_backend.Models;

import java.sql.Date;
import java.sql.Time;

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
@Table(name = "workingday")
public class WorkingDay {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workingDayId")
    private Long workingDayId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Date date;
    private Time checkInTime;
    private Time checkOutTime;
}
