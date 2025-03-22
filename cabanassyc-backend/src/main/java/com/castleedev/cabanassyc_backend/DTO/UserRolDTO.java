package com.castleedev.cabanassyc_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolDTO {
    
    private Long id;
    private Long userId;
    private Long rolId;
    private boolean state;

}
