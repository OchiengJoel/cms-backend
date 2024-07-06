package com.joe.cms.common.notification.dto;

import lombok.Data;

@Data
public class SmsSettingsDTO {

    private Long companyId;
    private String apiUrl;
    private String apiKey;
}
