//package com.joe.cms.projectmanagement.rest;
//
//import com.joe.cms.projectmanagement.dto.TaskDTO;
//import com.joe.cms.projectmanagement.service.PTaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/projects/{projectId}/tasks")
//public class PTaskController {
//
//    @Autowired
//    private PTaskService PTaskService;
//
//    // Retrieves all tasks for the specified project
//    @GetMapping("/list")
//    public ResponseEntity<?> getTasksByProject(@PathVariable Long projectId) {
//        try {
//            List<TaskDTO> tasks = PTaskService.getTasksByProject(projectId);
//            if (tasks.isEmpty()) {
//                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT); // No content if no tasks
//            }
//            return new ResponseEntity<>(tasks, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    //    @PostMapping("/listByIds")
////    @PostMapping("/{projectId}/listByIds")
////    public ResponseEntity<?> getTasksByIds(@PathVariable Long projectId, @RequestBody List<Long> taskIds) {
////    List<TaskDTO> tasks = taskService.getTasksByIds(projectId, taskIds);
////    return new ResponseEntity<>(tasks, HttpStatus.OK);
////    }
//
//    @PostMapping("/listByIds")
//    public ResponseEntity<?> getTasksByIds(@PathVariable Long projectId, @RequestBody List<Long> taskIds) {
//        try {
//            List<TaskDTO> tasks = PTaskService.getTasksByIds(projectId, taskIds);
//            return new ResponseEntity<>(tasks, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error fetching tasks: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
////    @PostMapping("/listByIds")
////    public ResponseEntity<List<TaskDTO>> getTasksByIds(@PathVariable Long projectId, @RequestBody List<Long> taskIds) {
////        List<TaskDTO> tasks = taskService.getTasksByIds(projectId, taskIds);
////        List<TaskDTO> taskDTOs = taskMapper.toTaskDTOs(tasks);
////        return ResponseEntity.ok(taskDTOs);
////    }
//
//
//    // View a specific task by task ID
//    @GetMapping("/{taskId}")
//    public ResponseEntity<?> viewTask(@PathVariable Long projectId, @PathVariable Long taskId) {
//        try {
//            TaskDTO task = PTaskService.getTaskByIdAndProjectId(taskId, projectId);
//            return new ResponseEntity<>(task, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Task not found.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Creates a new task with error handling
//    @PostMapping("/create")
//    public ResponseEntity<?> createTask(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
//        try {
//            taskDTO.setProjectId(projectId); // Ensure the project ID is set in the DTO
//            TaskDTO createdTask = PTaskService.createTask(taskDTO);
//            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred while creating the task.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Updates an existing task by task ID
//    @PutMapping("/update/{taskId}")
//    public ResponseEntity<?> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
//        try {
//            taskDTO.setProjectId(projectId);
//            taskDTO.setId(taskId); // Ensure the task ID is set
//            TaskDTO updatedTask = PTaskService.updateTask(taskDTO);
//            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Task not found or update failed.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Deletes a task by task ID
//    @DeleteMapping("/delete/{taskId}")
//    public ResponseEntity<?> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
//        try {
//            PTaskService.deleteTask(taskId, projectId);
//            return new ResponseEntity<>("Task Deleted...", HttpStatus.OK); // No content indicates successful deletion
//        } catch (Exception e) {
//            return new ResponseEntity<>("Task not found or could not be deleted.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Deletes selected tasks (by multiple IDs)
//    @DeleteMapping("/deleteSelected")
//    public ResponseEntity<?> deleteSelectedTasks(@PathVariable Long projectId, @RequestBody List<Long> taskIds) {
//        try {
//            PTaskService.deleteSelectedTasks(taskIds, projectId);
//            return new ResponseEntity<>("Selected Tasks Deleted...", HttpStatus.OK); // Indicates successful deletion
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred while deleting selected tasks.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//
