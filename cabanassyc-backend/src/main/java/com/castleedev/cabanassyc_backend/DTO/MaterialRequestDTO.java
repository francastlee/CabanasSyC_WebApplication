package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestDTO {
    
    private Long id;
    private Long userId;
    private Long materialId;
    private int quantity;
    private boolean state;

}
