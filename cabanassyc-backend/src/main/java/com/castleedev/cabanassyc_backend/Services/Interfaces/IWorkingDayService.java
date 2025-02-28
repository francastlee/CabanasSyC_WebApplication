package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.WorkingDay;

public interface IWorkingDayService {
    
    public List<WorkingDay> getAllWorkingDays();
    public WorkingDay getWorkingDayById(Long id);
    public WorkingDay addWorkingDay(WorkingDay workingDay);
    public WorkingDay updateWorkingDay(WorkingDay workingDay);
    public void deleteWorkingDay(Long id);
    
}
