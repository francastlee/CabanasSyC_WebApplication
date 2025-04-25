package com.castleedev.cabanassyc_backend.DAL;

import com.castleedev.cabanassyc_backend.Models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserDAL extends JpaRepository<UserModel, Long> {

    @Query("SELECT DISTINCT u FROM UserModel u LEFT JOIN FETCH u.userRolList WHERE u.state = true")
    List<UserModel> findAllByStateTrue();

    @Query("SELECT DISTINCT u FROM UserModel u LEFT JOIN FETCH u.userRolList WHERE u.id = :id AND u.state = true")
    Optional<UserModel> findByIdAndStateTrue(@Param("id") Long id);

    @Query("SELECT DISTINCT u FROM UserModel u LEFT JOIN FETCH u.userRolList ur LEFT JOIN FETCH ur.rol WHERE u.email = :email AND u.state = true")
    Optional<UserModel> findByEmailAndStateTrue(@Param("email") String email);

    boolean existsByEmailAndStateTrue(String email);
}