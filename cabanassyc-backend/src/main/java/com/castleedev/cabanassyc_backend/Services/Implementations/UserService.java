package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private IUserDAL userDAL;

    @Override
    public List<User> getAllUsers() {
        try {
            return userDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users", e);
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            return userDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user by id", e);
        }
    }

    @Override
    public User addUser(User user) {
        try {
            return userDAL.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error adding user", e);
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            return userDAL.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
    
}