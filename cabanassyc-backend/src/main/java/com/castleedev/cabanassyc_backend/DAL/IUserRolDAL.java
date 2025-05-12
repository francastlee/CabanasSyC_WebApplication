package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.UserRol;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRolDAL extends JpaRepository<UserRol, Long> {

    List<UserRol> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE UserRol c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<UserRol> findByIdAndStateTrue(Long id);
}
