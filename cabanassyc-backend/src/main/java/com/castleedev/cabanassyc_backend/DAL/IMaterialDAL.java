package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Material;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMaterialDAL extends JpaRepository<Material, Long> {
    
    List<Material> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Material c SET c.state = false WHERE c.materialId = :id")
    boolean softDeleteById(@Param("id") Long id);

    Material findByIdAndStateTrue(Long id);

}
