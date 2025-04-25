package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email es requerido")
    private String email;
    
    @NotBlank(message = "Contraseña es requerida")
    private String password;
}
