package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinEquipmentDTO {

    private Long id;
    
    @NotNull(message = "Cabin ID is required")
    private Long cabinId;
    
    @NotNull(message = "Equipment ID is required")
    private Long equipmentId;
    
    private boolean state;

}
