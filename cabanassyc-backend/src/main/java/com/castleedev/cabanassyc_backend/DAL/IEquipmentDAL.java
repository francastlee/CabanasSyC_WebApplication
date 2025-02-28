package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Equipment;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEquipmentDAL extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Equipment c SET c.state = false WHERE c.equipmentId = :id")
    boolean softDeleteById(@Param("id") Long id);

    Equipment findByIdAndStateTrue(Long id);

}
