package com.castleedev.cabanassyc_backend.Controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.castleedev.cabanassyc_backend.DTO.TaskDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(new ApiResponse<>(true, "Tasks found successfully", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable("id") Long id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task found successfully", task));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> addTask(@Valid @RequestBody TaskDTO dto) {
        TaskDTO created = taskService.addTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Task created successfully", created));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@Valid @RequestBody TaskDTO dto) {
        TaskDTO updated = taskService.updateTask(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task updated successfully", updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task deleted successfully", null));
    }
    @PostMapping("/{id}/finish")
    public ResponseEntity<ApiResponse<TaskDTO>> finishTask(@PathVariable("id") Long id) {
        TaskDTO finished = taskService.markFinished(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task finished successfully", finished));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getByAssigned(@PathVariable("userId") Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByAssignedTo(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tasks found successfully", tasks));
    }

    @GetMapping("/range")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {

        List<TaskDTO> tasks = taskService.getTasksByDateRange(from, to);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tasks found successfully", tasks));
    }
}
