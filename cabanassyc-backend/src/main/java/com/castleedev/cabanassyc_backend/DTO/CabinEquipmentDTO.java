package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinEquipmentDTO {

    private Long id;
    private Long cabinId;
    private Long equipmentId;
    private boolean state;

}
