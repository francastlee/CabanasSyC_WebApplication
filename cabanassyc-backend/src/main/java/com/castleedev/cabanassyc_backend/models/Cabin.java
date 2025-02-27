package com.castleedev.cabanassyc_backend.Models;

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
import lombok.Data;

@Entity
@Data
@Table(name = "cabin")
public class Cabin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinId")
    private Long cabinId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cabinTypeId")
    private CabinType cabinType;

    private boolean state;

    @OneToMany(mappedBy = "cabin")
    private List<CabinEquipment> cabinEquipmentList;

    @OneToMany(mappedBy = "cabin")
    private List<CabinBooking> cabinBookingList;
}
