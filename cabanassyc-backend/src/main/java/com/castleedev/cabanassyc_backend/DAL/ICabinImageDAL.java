package com.castleedev.cabanassyc_backend.DAL;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.castleedev.cabanassyc_backend.Models.CabinImage;

import jakarta.transaction.Transactional;


public interface ICabinImageDAL extends JpaRepository<CabinImage, Long> {
    
    List<CabinImage> findAllByStateTrue();

    @Modifying
    @Transactional
    @Query("UPDATE CabinImage c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<CabinImage> findByIdAndStateTrue(Long id); 
}
