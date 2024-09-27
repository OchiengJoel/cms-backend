package com.joe.cms.projectmanagement.service;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.enums.TaskStatus;
import com.joe.cms.projectmanagement.mapper.TaskMapper;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import com.joe.cms.projectmanagement.repo.ProjectRepo;
import com.joe.cms.projectmanagement.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private ProjectRepo projectRepository;

    @Autowired
    private TaskMapper taskMapper;

//    public List<TaskDTO> getAllTasks(Long projectId) {
//        return taskRepository.findByProjectId(projectId).stream()
//                .map(taskMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    public List<TaskDTO> getAllTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return taskMapper.toDTO(task);
    }

    public TaskDTO createTask(Long projectId, TaskDTO taskDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        Task task = taskMapper.toEntity(taskDTO);
        task.setProject(project);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        existingTask.setName(taskDTO.getName());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setDueDate(taskDTO.getDueDate());
        existingTask.setStatus(taskDTO.getStatus());
        return taskMapper.toDTO(taskRepository.save(existingTask));
    }

    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        taskRepository.deleteById(taskId);
    }

//    private final TaskRepo taskRepo;
//    private final ProjectRepo projectRepo;
//
//    @Autowired
//    public TaskService(TaskRepo taskRepo, ProjectRepo projectRepo) {
//        this.taskRepo = taskRepo;
//        this.projectRepo = projectRepo;
//    }
//
//    public List<TaskDTO> getTasksByProjectId(Long projectId) {
//        return taskRepo.findByProjectId(projectId)
//                .stream()
//                .map(TaskMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public TaskDTO getTaskById(Long taskId) {
//        Task task = taskRepo.findById(taskId)
//                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
//        return TaskMapper.toDTO(task);
//    }
//
//    public void createTask(TaskDTO taskDTO) {
//        Project project = projectRepo.findById(taskDTO.getProjectId())
//                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
//        Task task = TaskMapper.toEntity(taskDTO, project);
//        taskRepo.save(task);
//    }
//
//    public void updateTask(Long taskId, TaskDTO taskDTO) {
//        Task task = taskRepo.findById(taskId)
//                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
//        task.setName(taskDTO.getName());
//        task.setDescription(taskDTO.getDescription());
//        task.setDueDate(taskDTO.getDueDate());
//        task.setStatus(taskDTO.getStatus() != null ? taskDTO.getStatus() : TaskStatus.TODO);
//        taskRepo.save(task);
//    }
//
//    public void deleteTask(Long taskId) {
//        taskRepo.deleteById(taskId);
//    }
}
