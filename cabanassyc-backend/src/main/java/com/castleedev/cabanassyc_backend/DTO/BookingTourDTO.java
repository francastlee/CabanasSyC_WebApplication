package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTourDTO {
    
    private Long id;
    private Long bookingId;
    private Long tourId;
    private int people;
    private boolean state;

}
