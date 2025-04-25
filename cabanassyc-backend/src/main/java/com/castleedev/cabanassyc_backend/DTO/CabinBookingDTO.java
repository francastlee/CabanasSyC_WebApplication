package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinBookingDTO {
    
    private Long id;
    
    
    @NotNull(message = "Booking ID is required")
    private Long bookingId;
    
    @NotNull(message = "Cabin ID is required")
    private Long cabinId;
    
    @Min(value = 1, message = "At least one adult is required")
    private int adultsQuantity;
    
    @Min(value = 0, message = "Children quantity cannot be negative")
    private int childrenQuantity;
    
    private boolean state;

}
