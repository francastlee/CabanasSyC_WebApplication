package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestDTO {
    
    private Long id;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Material ID is required")
    private Long materialId;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    
    private boolean state;
}