package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Tour;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITourDAL extends JpaRepository<Tour, Long> {

    List<Tour> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Tour c SET c.state = false WHERE c.tourId = :id")
    boolean softDeleteById(@Param("id") Long id);

    Tour findByIdByStateTrue(Long id);

}
