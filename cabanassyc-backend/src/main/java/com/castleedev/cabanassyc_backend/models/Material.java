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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "material")
@AllArgsConstructor
@NoArgsConstructor
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materialId")
    private Long id;

    private String name;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "materialTypeId")
    private MaterialType materialType;

    private boolean state;

    @OneToMany(mappedBy = "material")
    private List<MaterialRequest> materialRequestList;

    public Material(Long id, String name, int stock, MaterialType materialType, boolean state) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.materialType = materialType;
        this.state = state;
    }
    
}
