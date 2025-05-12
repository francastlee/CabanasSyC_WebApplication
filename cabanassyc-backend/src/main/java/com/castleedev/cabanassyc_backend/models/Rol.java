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
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolId")
    private Long id;
    
    private String name;
    private boolean state;

    @OneToMany(mappedBy = "rol")
    private List<UserRol> userRolList;

    public Rol(Long id, String name, boolean state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
    
}
