package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Equipment;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEquipmentDAL extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Equipment c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<Equipment> findByIdAndStateTrue(Long id);

}
