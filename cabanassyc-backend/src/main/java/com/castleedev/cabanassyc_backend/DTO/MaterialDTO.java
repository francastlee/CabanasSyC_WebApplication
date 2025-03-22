package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDTO {
    
    private Long id;
    private String name;
    private int stock;
    private Long materialTypeId;
    private boolean state;

}
