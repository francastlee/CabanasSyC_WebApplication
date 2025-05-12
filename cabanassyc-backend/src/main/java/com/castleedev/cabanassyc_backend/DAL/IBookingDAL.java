package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Booking;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBookingDAL extends JpaRepository<Booking, Long> {

    List<Booking> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Booking c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<Booking> findByIdAndStateTrue(Long id);

}
