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
@Table(name = "cabinImage")
@AllArgsConstructor
@NoArgsConstructor
public class CabinImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinImageId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cabinId")
    private Cabin cabin;

    private String url;
    private boolean isCover;
    private boolean state;

}
