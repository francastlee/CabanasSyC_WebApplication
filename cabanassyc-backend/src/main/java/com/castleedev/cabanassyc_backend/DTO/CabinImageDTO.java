package com.castleedev.cabanassyc_backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabinImageDTO {

    private Long id;
    
    @NotNull(message = "Cabin ID is required")
    private Long cabinId;
    
    @NotBlank(message = "url is required")
    private String url;
    
    private boolean isCover;
    private boolean state;

}
