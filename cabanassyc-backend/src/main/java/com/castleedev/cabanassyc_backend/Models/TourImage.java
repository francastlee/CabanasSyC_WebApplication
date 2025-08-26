package com.castleedev.cabanassyc_backend.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tourimage")
public class TourImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private boolean isCover;

    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourid")
    private Tour tour;
}
