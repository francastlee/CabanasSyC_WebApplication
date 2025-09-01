package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ITaskDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DTO.TaskDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.Task;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITaskService;

@Service
@Transactional
public class TaskService implements ITaskService {

    private final ITaskDAL taskDAL;
    private final IUserDAL userDAL;
    private final ICabinDAL cabinDAL;

    public TaskService(ITaskDAL taskDAL, IUserDAL userDAL, ICabinDAL cabinDAL) {
        this.taskDAL = taskDAL;
        this.userDAL = userDAL;
        this.cabinDAL = cabinDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskDAL.findAllByStateTrue();
        if (tasks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tasks found");
        }
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskDAL.findByIdAndStateTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return convertToDTO(task);
    }

    @Override
    public TaskDTO addTask(TaskDTO dto) {
        UserModel assignedTo = findUserIfPresent(dto.getAssignedToId());
        UserModel createdBy = findUserIfPresent(dto.getCreatedById());
        Cabin cabin = findCabinIfPresent(dto.getCabinId());

        Task entity = convertToEntity(dto, assignedTo, createdBy, cabin);
        entity.setState(true);

        Task saved = taskDAL.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public TaskDTO updateTask(TaskDTO dto) {
        if (dto.getTaskId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id is required for update");
        }

        Task existing = taskDAL.findByIdAndStateTrue(dto.getTaskId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        UserModel assignedTo = findUserIfPresent(dto.getAssignedToId());
        UserModel createdBy = findUserIfPresent(dto.getCreatedById());
        Cabin cabin = findCabinIfPresent(dto.getCabinId());

        existing.setTaskDescription(dto.getTaskDescription());
        existing.setAssignedTo(assignedTo);
        existing.setCreatedBy(createdBy);
        existing.setCabin(cabin);
        existing.setTaskDate(dto.getTaskDate());
        existing.setFinished(dto.isFinished());
        existing.setState(dto.isState());

        Task updated = taskDAL.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void deleteTask(Long id) {
        taskDAL.findByIdAndStateTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        int rows = taskDAL.softDeleteById(id);
        if (rows <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete task");
        }
    }

    @Override
    public TaskDTO markFinished(Long id) {
        Task task = taskDAL.findByIdAndStateTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setFinished(true);
        Task saved = taskDAL.save(task);
        return convertToDTO(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByAssignedTo(Long userId) {
        List<Task> tasks = taskDAL.findAllByStateTrue().stream()
                .filter(t -> t.getAssignedTo() != null && userId.equals(t.getAssignedTo().getId()))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tasks for this user");
        }
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByDateRange(Date from, Date to) {
        List<Task> tasks = taskDAL.findAllByStateTrue().stream()
                .filter(t -> {
                    Date d = t.getTaskDate();
                    if (d == null) return false;
                    boolean okFrom = (from == null) || !d.before(from);
                    boolean okTo = (to == null) || !d.after(to);
                    return okFrom && okTo;
                })
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tasks in the given range");
        }
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getTaskId(),
                task.getTaskDescription(),
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null,
                task.getCreatedBy() != null ? task.getCreatedBy().getId() : null,
                task.getCabin() != null ? task.getCabin().getId() : null,
                task.getTaskDate(),
                task.isFinished(),
                task.isState()
        );
    }

    private Task convertToEntity(TaskDTO dto, UserModel assignedTo, UserModel createdBy, Cabin cabin) {
        Task task = new Task();
        task.setTaskId(dto.getTaskId());
        task.setTaskDescription(dto.getTaskDescription());
        task.setAssignedTo(assignedTo);
        task.setCreatedBy(createdBy);
        task.setCabin(cabin);
        task.setTaskDate(dto.getTaskDate());
        task.setFinished(dto.isFinished());
        task.setState(dto.isState());
        return task;
    }

    private UserModel findUserIfPresent(Long userId) {
        if (userId == null) return null;
        Optional<UserModel> u = userDAL.findByIdAndStateTrue(userId);
        return u.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    private Cabin findCabinIfPresent(Long cabinId) {
        if (cabinId == null) return null;
        Optional<Cabin> c = cabinDAL.findByIdAndStateTrue(cabinId);
        return c.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cabin not found"));
    }
}
