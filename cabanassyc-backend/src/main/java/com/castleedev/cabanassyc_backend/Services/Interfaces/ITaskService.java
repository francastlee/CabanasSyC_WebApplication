package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.sql.Date;
import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.TaskDTO;

public interface ITaskService {
    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(Long id);
    TaskDTO addTask(TaskDTO dto);
    TaskDTO updateTask(TaskDTO dto);
    void deleteTask(Long id);  
    TaskDTO markFinished(Long id);
    List<TaskDTO> getTasksByAssignedTo(Long userId);
    List<TaskDTO> getTasksByDateRange(Date from, Date to);
}
