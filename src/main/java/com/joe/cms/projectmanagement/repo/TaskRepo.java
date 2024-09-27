package com.joe.cms.projectmanagement.repo;

import com.joe.cms.projectmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    // Find tasks by project ID
    List<Task> findByProjectId(Long projectId);

    // Find a specific task by task ID and project ID
    Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);

    // Find multiple tasks by IDs and project ID
    List<Task> findByIdInAndProjectId(List<Long> taskIds, Long projectId);

    List<Task> findByIdIn(List<Long> taskIds);

  //  List<Task> findByProjectIdAndIdIn(Long projectId, List<Long> taskIds);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.id IN :taskIds")
    List<Task> findTasksByProjectAndIds(@Param("projectId") Long projectId, @Param("taskIds") List<Long> taskIds);

    List<Task> findByProjectIdAndIdIn(Long projectId, List<Long> taskIds);
    
}
