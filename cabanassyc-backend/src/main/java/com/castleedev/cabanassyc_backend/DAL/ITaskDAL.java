package com.castleedev.cabanassyc_backend.DAL;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.castleedev.cabanassyc_backend.Models.Task;

import jakarta.transaction.Transactional;

public interface ITaskDAL extends JpaRepository<Task, Long> {

    List<Task> findAllByStateTrue();

    Optional<Task> findByIdAndStateTrue(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Task t SET t.state = false WHERE t.id = :id AND t.state = true")
    int softDeleteById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Task t SET t.isFinished = true WHERE t.id = :id AND t.state = true")
    int markFinished(@Param("id") Long id);
}

