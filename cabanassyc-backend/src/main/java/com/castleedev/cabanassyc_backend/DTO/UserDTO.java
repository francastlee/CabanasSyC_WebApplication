package com.castleedev.cabanassyc_backend.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "Email es requerido")
    @Email(message = "Email debe ser válido")
    private String email;
    
    @NotBlank(message = "Nombre es requerido")
    private String firstName;
    
    @NotBlank(message = "Apellido es requerido")
    private String lastName;
    
    @NotBlank(message = "Contraseña es requerida")
    @Size(min = 8, message = "Contraseña debe tener al menos 8 caracteres")
    private String password;
    
    @PositiveOrZero(message = "Tarifa por hora debe ser positiva")
    @Nullable
    private Double hourlyRate;
    
    private Boolean state;

    public UserDTO(Long id, String firstName, String lastName, String email, double hourlyRate, boolean state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hourlyRate = hourlyRate;
        this.state = state;
    }

}
