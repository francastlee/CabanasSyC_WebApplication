package com.castleedev.cabanassyc_backend.DTO;

import java.time.LocalDate;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDayDTO {
    
    private Long id;
    private Long userId;
    private LocalDate date;
    private Time checkInTime;
    private Time checkOutTime;
    private boolean state;

}
