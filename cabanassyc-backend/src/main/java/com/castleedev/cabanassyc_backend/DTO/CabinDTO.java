package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinDTO {
    private Long id;
    
    @NotBlank(message = "Cabin name is required")
    @Size(max = 100, message = "Cabin name must be less than 100 characters")
    private String name;
    
    @NotNull(message = "Cabin type ID is required")
    private Long cabinTypeId;
    
    private boolean state;
}