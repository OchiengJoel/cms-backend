package com.joe.cms.projectmanagement.service;

import com.joe.cms.projectmanagement.dto.ProjectDTO;
import com.joe.cms.projectmanagement.mapper.ProjectMapper;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import com.joe.cms.projectmanagement.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private final ProjectRepo projectRepo;

    @Autowired
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepo projectRepo, ProjectMapper projectMapper) {
        this.projectRepo = projectRepo;
        this.projectMapper = projectMapper;
    }

    // Retrieves all projects for a given company and converts them to ProjectDTOs
    public List<ProjectDTO> getProjectsByCompany(Long companyId) {
        return projectRepo.findByCompanyId(companyId).stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    //retrieving projects with task IDs
    public List<ProjectDTO> getProjectsWithTaskIds(Long companyId) {
        List<Project> projects = projectRepo.findByCompanyId(companyId); // Assuming you have this method
        return projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = projectMapper.toDTO(project);
                    List<Long> taskIds = project.getTasks().stream().map(Task::getId).collect(Collectors.toList());
                    projectDTO.setTaskIds(taskIds);
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getProjectsWithTasksByCompanyId(Long companyId) {
        List<Project> projects = projectRepo.findByCompanyId(companyId);
        return projects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Creates a new project and returns the corresponding ProjectDTO
    public ProjectDTO createProject(ProjectDTO projectDTO){
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepo.save(project);
        return projectMapper.toDTO(project);
    }

    // View a single project by project ID and company ID
    public ProjectDTO getProjectByIdAndCompanyId(Long projectId, Long companyId) {
        Project project = projectRepo.findByIdAndCompanyId(projectId, companyId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toDTO(project);
    }


    // Updates an existing project
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        Project existingProject = projectRepo.findByIdAndCompanyId(projectDTO.getId(), projectDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Map the incoming DTO fields to the existing entity
        existingProject.setName(projectDTO.getName());
        existingProject.setDescription(projectDTO.getDescription());
        existingProject.setProjectStartDate(projectDTO.getProjectStartDate());
        existingProject.setProjectEndDate(projectDTO.getProjectEndDate());
        existingProject.setProjectStatus(projectDTO.getProjectStatus());  // Updating project status
        existingProject.setProjectLocation(projectDTO.getProjectLocation());
        existingProject.setProjectBudget(projectDTO.getProjectBudget());

        Project updatedProject = projectRepo.save(existingProject);
        return projectMapper.toDTO(updatedProject);
    }

    // Deletes a project by project ID and company ID
    public void deleteProject(Long projectId, Long companyId) {
        Project project = projectRepo.findByIdAndCompanyId(projectId, companyId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepo.delete(project);
    }

    // Deletes selected projects
    public void deleteSelectedProjects(List<Long> projectIds, Long companyId) {
        List<Project> projects = projectRepo.findByIdInAndCompanyId(projectIds, companyId);
        if (!projects.isEmpty()) {
            projectRepo.deleteAll(projects);
        }
    }
}
