package com.castleedev.cabanassyc_backend.DTO;

import java.sql.Time;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {
    
    private Long id;
    
    @NotBlank(message = "Tour name is required")
    @Size(min = 2, max = 100, message = "Tour name must be between 2 and 100 characters")
    private String name;
    
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;
    
    @NotNull(message = "Start time is required")
    private Time startTime;
    
    @NotNull(message = "End time is required")
    private Time endTime;
    
    private boolean state;
}