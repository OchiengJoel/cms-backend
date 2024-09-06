package com.joe.cms.projectmanagement.rest;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company/{companyId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Retrieves all tasks for the specified project
    @GetMapping("/list")
    public ResponseEntity<?> getTasksByProject(@PathVariable Long companyId, @PathVariable Long projectId) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByProject(projectId);
            if (tasks.isEmpty()) {
                // Returning an empty list with a NOT_FOUND status
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            // Returning an empty list with an INTERNAL_SERVER_ERROR status
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // View a specific task by task ID
    @GetMapping("/{taskId}")
    public ResponseEntity<?> viewTask(@PathVariable Long companyId, @PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            TaskDTO task = taskService.getTaskByIdAndProjectId(taskId, projectId);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            // Return a custom error message with a NOT_FOUND status
            return new ResponseEntity<>("Task not found.", HttpStatus.NOT_FOUND);
        }
    }

    // Creates a new task with error handling
    @PostMapping
    public ResponseEntity<?> createTask(@PathVariable Long companyId, @PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        try {
            taskDTO.setProjectId(projectId); // Ensure the project ID is set in the DTO
            TaskDTO createdTask = taskService.createTask(taskDTO);
            // Return the created task with a CREATED status
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            // In case of an error, return a message with an INTERNAL_SERVER_ERROR status
            return new ResponseEntity<>("An error occurred while creating the task.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing task by task ID
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long companyId, @PathVariable Long projectId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        try {
            taskDTO.setProjectId(projectId); // Ensure the project ID is set
            taskDTO.setId(taskId); // Ensure the task ID is set
            TaskDTO updatedTask = taskService.updateTask(taskDTO);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception e) {
            // Return an error message with a NOT_FOUND status
            return new ResponseEntity<>("Task not found or update failed.", HttpStatus.NOT_FOUND);
        }
    }

    // Deletes a task by task ID
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long companyId, @PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId, projectId);
            // Return a NO_CONTENT status for successful deletion
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return an error message with a NOT_FOUND status
            return new ResponseEntity<>("Task not found or could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }

    // Deletes selected tasks (by multiple IDs)
    @DeleteMapping("/deleteSelected")
    public ResponseEntity<?> deleteSelectedTasks(@PathVariable Long companyId, @PathVariable Long projectId, @RequestBody List<Long> taskIds) {
        try {
            taskService.deleteSelectedTasks(taskIds, projectId);
            // Return a NO_CONTENT status for successful deletion
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return a custom error message
            return new ResponseEntity<>("An error occurred while deleting selected tasks.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
