package com.joe.cms.projectmanagement.rest;

import com.joe.cms.projectmanagement.dto.TaskDTO;
import com.joe.cms.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/tasks")
@RequestMapping("/api/v1/projects/{projectId}/tasks")
public class TaskController {

        @Autowired
        private TaskService taskService;

//        @GetMapping
//        public List<TaskDTO> getTasks(@PathVariable Long projectId) {
//            return taskService.getAllTasks(projectId);
//        }

    @GetMapping
    public List<TaskDTO> getTasks(@PathVariable Long projectId) {
        System.out.println("Fetching tasks for project ID: " + projectId);
        return taskService.getAllTasks(projectId);
    }

        @GetMapping("/{taskId}")
        public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
            TaskDTO taskDTO = taskService.getTaskById(taskId);
            return ResponseEntity.ok(taskDTO);
        }

        @PostMapping("/create")
        public TaskDTO createTask(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
            return taskService.createTask(projectId, taskDTO);
        }

        @PutMapping("/update/{taskId}")
        public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
            return taskService.updateTask(taskId, taskDTO);
        }

        @DeleteMapping("/{taskId}")
        public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
            taskService.deleteTask(taskId);
            return ResponseEntity.noContent().build();
        }


//    private final TaskService taskService;
//
//    @Autowired
//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }
//
//    @GetMapping("/project/{projectId}")
//    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable Long projectId) {
//        List<TaskDTO> tasks = taskService.getTasksByProjectId(projectId);
//        return ResponseEntity.ok(tasks);
//    }
//
//    @GetMapping("/{taskId}")
//    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
//        TaskDTO task = taskService.getTaskById(taskId);
//        return ResponseEntity.ok(task);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
//        taskService.createTask(taskDTO);
//        return ResponseEntity.ok("Task created successfully");
//    }
//
//    @PutMapping("/{taskId}")
//    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
//        taskService.updateTask(taskId, taskDTO);
//        return ResponseEntity.ok("Task updated successfully");
//    }
//
//    @DeleteMapping("/{taskId}")
//    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
//        taskService.deleteTask(taskId);
//        return ResponseEntity.ok("Task deleted successfully");
//    }
}
