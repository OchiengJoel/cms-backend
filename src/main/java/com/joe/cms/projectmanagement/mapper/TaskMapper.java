package com.joe.cms.projectmanagement.mapper;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.enums.TaskStatus;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import com.joe.cms.projectmanagement.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    // Converts Task entity to TaskDTO
//    public TaskDTO toDTO(Task task) {
//        if (task == null) {
//            return null;
//        }

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setProjectId(task.getProject().getId());
        return dto;
    }

//    public List<TaskDTO> toTaskDTOs(List<Task> tasks) {
//        if (tasks == null) {
//            return Collections.emptyList();
//        }
//        return tasks.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    // Converts TaskDTO to Task entity
//    public Task toEntity(TaskDTO taskDTO, Project project) {
//        if (dto == null) {
//            return null;
//        }

   // public static Task toEntity(TaskDTO taskDTO, Project project)
    public Task toEntity(TaskDTO taskDTO){
        if (taskDTO == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
//        task.setStatus(dto.getStatus());
        task.setStatus(taskDTO.getStatus() != null ? taskDTO.getStatus() : TaskStatus.TODO); // Default to TODO
        task.setDueDate(taskDTO.getDueDate());
        //task.setProject(project);
        return task;
    }

    // Updates an existing Task entity with TaskDTO data
    public void updateEntity(Task task, TaskDTO dto) {
        if (dto == null) {
            return;
        }

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        // Note: Project association is handled separately if needed
    }
}
