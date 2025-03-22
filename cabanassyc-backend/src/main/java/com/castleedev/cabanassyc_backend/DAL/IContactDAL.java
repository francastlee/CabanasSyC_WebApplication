package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.Contact;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IContactDAL extends JpaRepository<Contact, Long> {

    List<Contact> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.state = false WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);

    Contact findByIdAndStateTrue(Long id);

}
