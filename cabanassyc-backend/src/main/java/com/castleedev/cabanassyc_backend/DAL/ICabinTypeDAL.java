package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.CabinType;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICabinTypeDAL extends JpaRepository<CabinType, Long> {
    
    List<CabinType> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE CabinType c SET c.state = false WHERE c.cabinTypeId = :id")
    boolean softDeleteById(@Param("id") Long id);

    CabinType findByIdAndStateTrue(Long id);

}
