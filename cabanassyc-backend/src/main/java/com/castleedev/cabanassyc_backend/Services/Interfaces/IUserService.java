package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.User;

public interface IUserService {
        
    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User addUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long id);

}
