package com.castleedev.cabanassyc_backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinFullDTO {
    private Long id;
    private String name;
    private boolean state;
    private CabinTypeDTO cabinType;
    private List<EquipmentDTO> equipments;
    private List<CabinImageDTO> cabinImages;
}
