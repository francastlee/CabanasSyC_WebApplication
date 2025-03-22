package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.User;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserDAL extends JpaRepository<User, Long> {

    List<User> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE User c SET c.state = false WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);

    User findByIdAndStateTrue(Long id);

}
