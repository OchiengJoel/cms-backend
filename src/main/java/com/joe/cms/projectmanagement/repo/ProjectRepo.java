package com.joe.cms.projectmanagement.repo;

import com.joe.cms.projectmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    // Custom query to find projects by company ID
    List<Project> findByCompanyId(Long companyId);
}
