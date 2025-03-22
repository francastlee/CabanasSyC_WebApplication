package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinTypeDTO {
    
    private Long id;
    private String name;
    private int capacity;
    private Double price;
    private boolean state;

}
