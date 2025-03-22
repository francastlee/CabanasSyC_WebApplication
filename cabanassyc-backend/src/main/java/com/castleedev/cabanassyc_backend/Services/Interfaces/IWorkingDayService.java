package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;

public interface IWorkingDayService {
    
    public List<WorkingDayDTO> getAllWorkingDays();
    public WorkingDayDTO getWorkingDayById(Long id);
    public WorkingDayDTO addWorkingDay(WorkingDayDTO workingDayDTO);
    public WorkingDayDTO updateWorkingDay(WorkingDayDTO workingDayDTO);
    public void deleteWorkingDay(Long id);
    
}
