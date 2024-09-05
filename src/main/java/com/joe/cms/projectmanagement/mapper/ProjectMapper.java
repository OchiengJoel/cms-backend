package com.joe.cms.projectmanagement.mapper;

import com.joe.cms.company.repo.CompanyRepo;
import com.joe.cms.projectmanagement.dto.ProjectDTO;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    @Autowired
    private final CompanyRepo companyRepo;

    public ProjectMapper(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    //Convert Project Entity to ProjectDTO
    public ProjectDTO toDTO(Project project){
       ProjectDTO dto = new ProjectDTO();
       dto.setId(project.getId());
       dto.setName(project.getName());
       dto.setDescription(project.getDescription());
       dto.setCompanyId(project.getCompany().getId());
       dto.setTaskIds(project.getTasks().stream()
               .map(Task::getId)
               .collect(Collectors.toList()));
       return dto;
    }

    //Convert ProjectDTO to Project Entity
    public Project toEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());

        // Fetch the associated Company entity using the companyId from the DTO
        project.setCompany(companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found")));

        return project;
    }

}
