package com.castleedev.cabanassyc_backend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "materialrequest")
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materialRequestId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "materialId")
    private Material material;

    private int quantity;
    boolean state;

}
