package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.CabinEquipment;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICabinEquipmentDAL extends JpaRepository<CabinEquipment, Long> {

    List<CabinEquipment> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE CabinEquipment c SET c.state = false WHERE c.cabinEquipmentId = :id")
    boolean softDeleteById(@Param("id") Long id);

    CabinEquipment findByIdAndStateTrue(Long id);

}
