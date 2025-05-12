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
@Table(name="equipment")
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="equipmentId")
    private Long id;

    private String name;
    private boolean state;

    @OneToMany(mappedBy = "equipment")
    private List<CabinEquipment> cabinEquipmentList;

    public Equipment(Long id, String name, boolean state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

}
