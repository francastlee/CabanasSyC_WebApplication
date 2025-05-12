package com.castleedev.cabanassyc_backend.Models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "contact")
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
    private Date date;
    private boolean read;
    private boolean state;
    
}
