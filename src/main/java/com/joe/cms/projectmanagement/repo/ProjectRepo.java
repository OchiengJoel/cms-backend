package com.joe.cms.projectmanagement.repo;

import com.joe.cms.projectmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    // Custom query to find projects by company ID
    List<Project> findByCompanyId(Long companyId);
    Optional<Project> findByIdAndCompanyId(Long projectId, Long companyId);
    List<Project> findByIdInAndCompanyId(List<Long> projectIds, Long companyId);

}
