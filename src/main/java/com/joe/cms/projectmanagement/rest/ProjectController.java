package com.joe.cms.projectmanagement.rest;

import com.joe.cms.parcel.dto.ParcelDTO;
import com.joe.cms.projectmanagement.dto.ProjectDTO;
import com.joe.cms.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/company/{companyId}/projects")
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Endpoint to retrieve projects by company ID
//    @GetMapping("/company/{companyId}")
//    public List<ProjectDTO> getProjectsByCompany(@PathVariable Long companyId) {
//        return projectService.getProjectsByCompany(companyId);
//    }

    // Retrieves projects for the specified company with error handling
//    @GetMapping("/list")
//    public ResponseEntity<List<?>> getProjectsByCompany(@PathVariable Long companyId) {
//        try {
//            List<ProjectDTO> projects = projectService.getProjectsByCompany(companyId);
//            if (projects.isEmpty()) {
//                return new ResponseEntity<>("No projects found for the specified company.", HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(projects, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred while retrieving projects.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Retrieves all projects for the specified company
    @GetMapping("/list")
    public ResponseEntity<?> getProjectsByCompany(@PathVariable Long companyId) {
        try {
            List<ProjectDTO> projects = projectService.getProjectsByCompany(companyId);
            if (projects.isEmpty()) {
                // Returning an empty list with a NOT_FOUND status
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            // Returning an empty list with an INTERNAL_SERVER_ERROR status
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // View a specific project by project ID
    @GetMapping("/{projectId}")
    public ResponseEntity<?> viewProject(@PathVariable Long companyId, @PathVariable Long projectId) {
        try {
            ProjectDTO project = projectService.getProjectByIdAndCompanyId(projectId, companyId);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            // Return a custom error message with a NOT_FOUND status
            return new ResponseEntity<>("Project not found.", HttpStatus.NOT_FOUND);
        }
    }

    // Creates a new project with error handling
//    @PostMapping("/create")
//    public ResponseEntity<?> createProject(@PathVariable Long companyId, @RequestBody ProjectDTO projectDTO) {
//        try {
//            projectDTO.setCompanyId(companyId); // Ensure the company ID is set in the DTO
//            ProjectDTO createdProject = projectService.createProject(projectDTO);
//            // Return the created project with a CREATED status
//            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
//        } catch (Exception e) {
//            // In case of an error, return a message with an INTERNAL_SERVER_ERROR status
//            return new ResponseEntity<>("An error occurred while creating the project.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@PathVariable Long companyId, @RequestBody ProjectDTO projectDTO) {
        try {
            projectDTO.setCompanyId(companyId); // Ensure the company ID is set in the DTO
            System.out.println("Creating project: " + projectDTO); // Log the DTO
            ProjectDTO createdProject = projectService.createProject(projectDTO);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();  // Print full stack trace for debugging
            return new ResponseEntity<>("An error occurred while creating the project.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing project by project ID
    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long companyId, @PathVariable Long projectId, @RequestBody ProjectDTO projectDTO) {
        try {
            projectDTO.setCompanyId(companyId); // Ensure the company ID is set
            projectDTO.setId(projectId); // Ensure the project ID is set
            ProjectDTO updatedProject = projectService.updateProject(projectDTO);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (Exception e) {
            // Return an error message with a NOT_FOUND status
            return new ResponseEntity<>("Project not found or update failed.", HttpStatus.NOT_FOUND);
        }
    }

    // Deletes a project by project ID
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long companyId, @PathVariable Long projectId) {
        try {
            projectService.deleteProject(projectId, companyId);
            // Return a NO_CONTENT status for successful deletion
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return an error message with a NOT_FOUND status
            return new ResponseEntity<>("Project not found or could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }

    // Deletes selected projects (by multiple IDs)
    @DeleteMapping("/deleteSelected")
    public ResponseEntity<?> deleteSelectedProjects(@PathVariable Long companyId, @RequestBody List<Long> projectIds) {
        try {
            projectService.deleteSelectedProjects(projectIds, companyId);
            // Return a NO_CONTENT status for successful deletion
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return a custom error message
            return new ResponseEntity<>("An error occurred while deleting selected projects.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
