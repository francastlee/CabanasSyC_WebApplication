package com.castleedev.cabanassyc_backend.DTO;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Task description is required")
    @Size(min = 5, max = 500, message = "Task description must be between 5 and 500 characters")
    private String taskDescription;

    private Long assignedToId;
    private Long createdById;
    private Long cabinId;

    @NotNull(message = "Task date is required")
    private Date taskDate;

    private boolean isFinished;

    private boolean state;
}