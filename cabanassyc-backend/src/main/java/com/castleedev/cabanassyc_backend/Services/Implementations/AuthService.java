package com.castleedev.cabanassyc_backend.Services.Implementations;

import javax.management.relation.RoleNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.RolDTO;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Exceptions.UserAlreadyExistsException;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IAuthService;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;

@Service
public class AuthService implements IAuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final IUserDAL userDAL;
    private final IRolDAL rolDAL;
    private final IUserService userService;
    private final IUserRolService userRolService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserDAL userDAL, 
                     IRolDAL rolDAL,
                     IUserService userService,
                     IUserRolService userRolService,
                     PasswordEncoder passwordEncoder) {
        this.userDAL = userDAL;
        this.rolDAL = rolDAL;
        this.userService = userService;
        this.userRolService = userRolService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        try {
            if (userDAL.existsByEmailAndStateTrue(userDTO.getEmail())) {
                logger.warn("Intento de registro con email existente: {}", userDTO.getEmail());
                throw new UserAlreadyExistsException("El email ya está registrado");
            }
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(hashedPassword);
            userDTO.setState(true);
            UserDTO createdUser = userService.addUser(userDTO);

            RolDTO userRole = convertToDTO(rolDAL.findByNameAndStateTrue("USER")
                    .orElseThrow(() -> new RoleNotFoundException("Rol USER no encontrado")));

            UserRolDTO userRolDTO = new UserRolDTO();
            userRolDTO.setUserId(createdUser.getId());
            userRolDTO.setRolId(userRole.getId());
            userRolDTO.setState(true);
            
            userRolService.addUserRol(userRolDTO);

            logger.info("Usuario registrado exitosamente: {}", createdUser.getEmail());
            return createdUser;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario", e);
        }
    }

    @Override
    public boolean verifyCredentials(String email, String rawPassword) {
        UserDTO user = convertToDTO(userDAL.findByEmailAndStateTrue(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas")));
        
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    private UserDTO convertToDTO(UserModel userModel) {
        if (userModel == null) return null;
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userModel.getId());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setFirstName(userModel.getFirstName());
        userDTO.setLastName(userModel.getLastName());
        userDTO.setPassword(userModel.getPasswordHashed());
        userDTO.setHourlyRate(userModel.getHourlyRate());
        userDTO.setState(userModel.isState());
        
        return userDTO;
    }

    private RolDTO convertToDTO(Rol rol) {
        if (rol == null) return null;
        
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setName(rol.getName());
        rolDTO.setState(rol.isState());
        
        return rolDTO;
    }
}