package com.joe.cms.company.repo;

import com.joe.cms.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    boolean existsByCompanyName(String companyName);
}
