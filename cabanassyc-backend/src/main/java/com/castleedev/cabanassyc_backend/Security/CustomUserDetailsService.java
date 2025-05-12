package com.castleedev.cabanassyc_backend.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Models.UserRol;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserDAL userDAL;

    public CustomUserDetailsService(IUserDAL userDAL) {
        this.userDAL = userDAL;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.trim().isEmpty()) {
            throw new UsernameNotFoundException("El email no puede estar vacío");
        }

        UserModel user = userDAL.findByEmailAndStateTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        if (!user.isState()) {
            throw new UsernameNotFoundException("El usuario está desactivado");
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHashed())
                .disabled(!user.isState())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(getAuthorities(user))
                .build();
    }

    private Collection<GrantedAuthority> getAuthorities(UserModel user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (user.getUserRolList() != null) {
            for (UserRol userRol : user.getUserRolList()) {
                if (userRol != null && userRol.getRol() != null && userRol.getRol().getName() != null) {
                    authorities.add(new SimpleGrantedAuthority(
                        "ROLE_" + userRol.getRol().getName().toUpperCase()
                    ));
                }
            }
        }

        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        return authorities;
    }
}