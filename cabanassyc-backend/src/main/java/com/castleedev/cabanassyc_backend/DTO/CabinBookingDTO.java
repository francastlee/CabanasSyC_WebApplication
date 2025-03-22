package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinBookingDTO {
    
    private Long id;
    private Long bookingId;
    private Long cabinId;
    private int adultsQuantity;
    private int childrenQuantity;
    private boolean state;

}
