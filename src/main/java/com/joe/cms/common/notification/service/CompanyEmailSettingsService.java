package com.joe.cms.common.notification.service;

import com.joe.cms.common.notification.repo.CompanyEmailSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyEmailSettingsService {

    @Autowired
    private final CompanyEmailSettingsRepository companyEmailSettingsRepository;

    public CompanyEmailSettingsService(CompanyEmailSettingsRepository companyEmailSettingsRepository) {
        this.companyEmailSettingsRepository = companyEmailSettingsRepository;
    }
}
