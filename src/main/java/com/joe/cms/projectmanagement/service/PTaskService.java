//package com.joe.cms.projectmanagement.service;
//
//import com.joe.cms.projectmanagement.dto.TaskDTO;
//import com.joe.cms.projectmanagement.mapper.TaskMapper;
//import com.joe.cms.projectmanagement.model.Project;
//import com.joe.cms.projectmanagement.model.Task;
//import com.joe.cms.projectmanagement.repo.ProjectRepo;
//import com.joe.cms.projectmanagement.repo.TaskRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class PTaskService {
//
//    @Autowired
//    private TaskRepo taskRepository;
//
//    @Autowired
//    private ProjectRepo projectRepository;
//
//    @Autowired
//    private TaskMapper taskMapper;
//
//    // Retrieves all tasks for a given project
//    public List<TaskDTO> getTasksByProject(Long projectId) {
//        return taskRepository.findByProjectId(projectId).stream()
//                .map(taskMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    // View a single task by task ID and project ID
//    public TaskDTO getTaskByIdAndProjectId(Long taskId, Long projectId) {
//        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//        return taskMapper.toDTO(task);
//    }
//
//   // public List<TaskDTO> getTasksByIds(List<Long> taskIds) {
//     //   List<Task> tasks = taskRepository.findAllById(taskIds);
//       // return tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
//    //}
//
////    public List<TaskDTO> getTasksByIds(List<Long> taskIds) {
////        List<Task> tasks = taskRepository.findByIdIn(taskIds);  // Assuming taskRepository has this method
////        return tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
////    }
//
////    public List<TaskDTO> getTasksByIds(Long projectId, List<Long> taskIds) {
////        List<Task> tasks = taskRepository.findByProjectIdAndIdIn(projectId, taskIds);
////        return tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
////        //return taskRepository.findByProjectIdAndIdIn(projectId, taskIds);
////    }
//
//    public List<TaskDTO> getTasksByIds(Long projectId, List<Long> taskIds) {
//        List<Task> tasks = taskRepository.findByProjectIdAndIdIn(projectId, taskIds);
//        return tasks.stream().map(task -> new TaskMapper().toDTO(task)).collect(Collectors.toList());
//    }
//
//
//    // Creates a new task
//    public TaskDTO createTask(TaskDTO taskDTO) {
//        Project project = projectRepository.findById(taskDTO.getProjectId())
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        Task task = taskMapper.toEntity(taskDTO, project);
//        task = taskRepository.save(task);
//        return taskMapper.toDTO(task);
//    }
//
//    // Updates an existing task
//    public TaskDTO updateTask(TaskDTO taskDTO) {
//        Task existingTask = taskRepository.findByIdAndProjectId(taskDTO.getId(), taskDTO.getProjectId())
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//
//        taskMapper.updateEntity(existingTask, taskDTO);
//        Task updatedTask = taskRepository.save(existingTask);
//        return taskMapper.toDTO(updatedTask);
//    }
//
//    // Deletes a task by task ID and project ID
//    public void deleteTask(Long taskId, Long projectId) {
//        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//        taskRepository.delete(task);
//    }
//
//    // Deletes selected tasks by IDs and project ID
//    public void deleteSelectedTasks(List<Long> taskIds, Long projectId) {
//        List<Task> tasks = taskRepository.findByIdInAndProjectId(taskIds, projectId);
//        if (!tasks.isEmpty()) {
//            taskRepository.deleteAll(tasks);
//        }
//    }
//
//}
