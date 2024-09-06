package com.joe.cms.projectmanagement.mapper;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import com.joe.cms.projectmanagement.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    // Converts Task entity to TaskDTO
    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setProjectId(task.getProject().getId());
        return dto;
    }

    // Converts TaskDTO to Task entity
    public Task toEntity(TaskDTO dto, Project project) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setProject(project);
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
