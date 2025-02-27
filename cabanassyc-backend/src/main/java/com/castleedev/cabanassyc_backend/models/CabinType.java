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
@Table(name = "cabintype")
public class CabinType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinTypeId")
    private Long cabinTypeId;
    
    private String name;
    private int capacity;
    private Double price;
    private boolean state;

    @OneToMany(mappedBy = "cabinType")
    private List<Cabin> cabins;

}