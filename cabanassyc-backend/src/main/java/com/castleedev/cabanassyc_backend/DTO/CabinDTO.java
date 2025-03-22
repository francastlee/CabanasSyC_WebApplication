package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinDTO {
    
    private Long id;
    private String name;
    private Long cabinTypeId;
    private boolean state;
    
}
