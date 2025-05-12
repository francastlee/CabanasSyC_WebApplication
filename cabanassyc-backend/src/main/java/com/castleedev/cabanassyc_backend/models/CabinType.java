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
@Table(name = "cabintype")
@AllArgsConstructor
@NoArgsConstructor
public class CabinType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinTypeId")
    private Long id;
    
    private String name;
    private int capacity;
    private Double price;
    private boolean state;

    @OneToMany(mappedBy = "cabinType")
    private List<Cabin> cabins;

    public CabinType(Long id, String name, int capacity, Double price, boolean state) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.state = state;
    }

}