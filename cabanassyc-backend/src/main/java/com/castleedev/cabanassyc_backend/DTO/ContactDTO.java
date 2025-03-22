package com.castleedev.cabanassyc_backend.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
    private Date date;
    private boolean read;
    private boolean state;

}
