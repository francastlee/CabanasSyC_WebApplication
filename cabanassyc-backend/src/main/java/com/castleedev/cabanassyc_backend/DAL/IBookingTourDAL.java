package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.BookingTour;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBookingTourDAL extends JpaRepository<BookingTour, Long> {

    List<BookingTour> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE BookingTour c SET c.state = false WHERE c.bookingTourId = :id")
    boolean softDeleteById(@Param("id") Long id);

    BookingTour findByIdAndStateTrue(Long id);

}
