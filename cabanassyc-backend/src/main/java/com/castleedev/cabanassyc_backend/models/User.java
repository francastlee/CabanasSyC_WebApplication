package com.castleedev.cabanassyc_backend.Models;

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
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;
    
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

    public User(Long id, String firstName, String lastName, String email, String passwordHashed, double hourlyRate, boolean state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHashed = passwordHashed;
        this.hourlyRate = hourlyRate;
        this.state = state;
    }
    
}