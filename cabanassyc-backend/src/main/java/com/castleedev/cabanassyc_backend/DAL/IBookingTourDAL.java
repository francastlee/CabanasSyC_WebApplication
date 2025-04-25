package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.BookingTour;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBookingTourDAL extends JpaRepository<BookingTour, Long> {

    @Query("SELECT b FROM BookingTour b WHERE b.state = true")
    List<BookingTour> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE BookingTour c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<BookingTour> findByIdAndStateTrue(Long id);

}
