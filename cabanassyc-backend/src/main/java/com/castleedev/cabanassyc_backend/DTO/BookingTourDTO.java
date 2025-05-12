package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTourDTO {
    
    private Long id;
    
    @NotNull(message = "Booking ID is required")
    private Long bookingId;
    
    @NotNull(message = "Tour ID is required")
    private Long tourId;
    
    @Min(value = 1, message = "People must be at least 1")
    private int people;
    
    private boolean state;

}
