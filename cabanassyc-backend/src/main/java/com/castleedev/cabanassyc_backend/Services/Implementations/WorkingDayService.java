package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IWorkingDayDAL;
import com.castleedev.cabanassyc_backend.Models.WorkingDay;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IWorkingDayService;

@Service
public class WorkingDayService implements IWorkingDayService {

    @Autowired
    private IWorkingDayDAL workingDayDAL;

    @Override
    public List<WorkingDay> getAllWorkingDays() {
        try {
            return workingDayDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all working days", e);
        }
    }

    @Override
    public WorkingDay getWorkingDayById(Long id) {
        try {
            return workingDayDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting working day by id", e);
        }
    }

    @Override
    public WorkingDay addWorkingDay(WorkingDay workingDay) {
        try {
            return workingDayDAL.save(workingDay);
        } catch (Exception e) {
            throw new RuntimeException("Error adding working day", e);
        }
    }

    @Override
    public WorkingDay updateWorkingDay(WorkingDay workingDay) {
        try {
            return workingDayDAL.save(workingDay);
        } catch (Exception e) {
            throw new RuntimeException("Error updating working day", e);
        }
    }

    @Override
    public void deleteWorkingDay(Long id) {
        try {
            workingDayDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting working day", e);
        }
    }
    
}