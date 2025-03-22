package com.castleedev.cabanassyc_backend.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long id;
    private Long userId;
    private LocalDate date;
    private Double totalPrice;
    private boolean state;

}
