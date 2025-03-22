package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private double hourlyRate;
    private boolean state;

    public UserDTO(Long id, String firstName, String lastName, String email, double hourlyRate, boolean state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hourlyRate = hourlyRate;
        this.state = state;
    }

}
