package com.joe.cms.common.notification.repo;

import com.joe.cms.common.notification.model.CompanySmsSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsSettingsRepository extends JpaRepository<CompanySmsSettings, Long> {


    Optional<CompanySmsSettings> findByCompanyId(Long companyId);

}
