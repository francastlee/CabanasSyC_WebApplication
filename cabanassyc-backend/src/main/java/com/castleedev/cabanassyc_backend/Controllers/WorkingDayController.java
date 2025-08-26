package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IWorkingDayService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/working-days")
public class WorkingDayController {
    
    private final IWorkingDayService workingDayService;

    public WorkingDayController(IWorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkingDayDTO>>> getAllWorkingDays() {
        List<WorkingDayDTO> workingDays = workingDayService.getAllWorkingDays();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Working days found successfully", workingDays)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkingDayDTO>> getWorkingDayById(@PathVariable("id") Long id) {
        WorkingDayDTO workingDay = workingDayService.getWorkingDayById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Working day found successfully", workingDay)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkingDayDTO>> addWorkingDay(@Valid @RequestBody WorkingDayDTO workingDayDTO) {
        WorkingDayDTO createdWorkingDay = workingDayService.addWorkingDay(workingDayDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Working day created successfully", createdWorkingDay));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<WorkingDayDTO>> updateWorkingDay(@Valid @RequestBody WorkingDayDTO workingDayDTO) {
        WorkingDayDTO updatedWorkingDay = workingDayService.updateWorkingDay(workingDayDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Working day updated successfully", updatedWorkingDay)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkingDay(@PathVariable("id") Long id) {
        workingDayService.deleteWorkingDay(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Working day deleted successfully", null)
        );
    }
}