package com.castleedev.cabanassyc_backend.models;

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
@Table(name = "cabinequipment")
public class CabinEquipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinEquipmentId")
    private Long cabinEquipmentId;

    @ManyToOne
    @JoinColumn(name = "cabinId")
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;

    private boolean state;
}
