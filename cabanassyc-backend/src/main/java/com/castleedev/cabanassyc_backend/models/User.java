package com.castleedev.cabanassyc_backend.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHashed;
    private double hourlyRate; //Null is allowed
    private boolean state;

    @OneToMany(mappedBy = "user")
    private List<UserRol> userRolList;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "user")
    private List<MaterialRequest> materialRequestList;

    @OneToMany(mappedBy = "user")
    private List<WorkingDay> workingDayList;
}