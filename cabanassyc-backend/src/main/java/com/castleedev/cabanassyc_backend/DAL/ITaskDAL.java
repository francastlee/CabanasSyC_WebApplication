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
    @Modifying
    @Transactional
    @Query("UPDATE Task c SET c.state = false WHERE c.id = :id")
    int softDeleteById(@Param("id") Long id);
    Optional<Task> findByIdAndStateTrue(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Task t SET t.isFinished = true WHERE t.taskId = :taskId AND t.state = true")
    int markFinished(@Param("taskId") Long taskId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Task t SET t.taskDescription = :desc WHERE t.taskId = :taskId AND t.state = true")
    int updateDescription(@Param("taskId") Long taskId, @Param("desc") String description);
}
