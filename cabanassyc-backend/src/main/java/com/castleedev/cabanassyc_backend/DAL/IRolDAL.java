package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Rol;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRolDAL extends JpaRepository<Rol, Long> {

    List<Rol> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Rol c SET c.state = false WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);

    Rol findByIdAndStateTrue(Long id);

}
