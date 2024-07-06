package com.joe.cms.common.notification.repo;

import com.joe.cms.common.notification.model.CompanyEmailSettings;
import com.joe.cms.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyEmailSettingsRepository extends JpaRepository<CompanyEmailSettings, Long> {

    Optional<CompanyEmailSettings> findByCompanyId(Long companyId);
    Optional<CompanyEmailSettings> findByCompany(Company company);


}
