package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.MaterialRequest;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMaterialRequestDAL extends JpaRepository<MaterialRequest, Long> {

    List<MaterialRequest> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE MaterialRequest c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<MaterialRequest> findByIdAndStateTrue(Long id);

}