package com.castleedev.cabanassyc_backend.models;

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
@Table(name = "material")
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materialId")
    private Long materialId;

    private String name;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "materialTypeId")
    private MaterialType materialType;

    private boolean state;

    @OneToMany(mappedBy = "material")
    private List<MaterialRequest> materialRequestList;
    
}
