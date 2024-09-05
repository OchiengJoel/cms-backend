package com.joe.cms.projectmanagement.mapper;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import com.joe.cms.projectmanagement.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    @Autowired
    private final ProjectRepo projectRepo;

    public TaskMapper(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    // Converts Task entity to TaskDTO
    public TaskDTO toDTO(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setProjectId(task.getProject().getId());
        return dto;
    }

    // Converts TaskDTO to Task entity
    public Task toEntity(TaskDTO dto){
        Task task = new Task();
        task.setId(dto.getId());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        // Fetch the associated Project entity using the projectId from the DTO
        Project project = projectRepo.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);
        return task;
    }
}
