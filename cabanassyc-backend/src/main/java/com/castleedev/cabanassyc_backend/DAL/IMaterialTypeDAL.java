package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.MaterialType;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMaterialTypeDAL extends JpaRepository<MaterialType, Long> {

    List<MaterialType> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE MaterialType c SET c.state = false WHERE c.materialTypeId = :id")
    boolean softDeleteById(@Param("id") Long id);

    MaterialType findByIdAndStateTrue(Long id);

}
