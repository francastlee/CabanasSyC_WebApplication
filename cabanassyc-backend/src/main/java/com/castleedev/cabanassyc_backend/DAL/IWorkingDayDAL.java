package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.WorkingDay;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IWorkingDayDAL extends JpaRepository<WorkingDay, Long> {

    List<WorkingDay> findAllByStateTrue();
    
    @Modifying
    @Transactional
    @Query("UPDATE WorkingDay c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);

    Optional<WorkingDay> findByIdAndStateTrue(Long id);

}
