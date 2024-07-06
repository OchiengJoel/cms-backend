package com.joe.cms.common.notification.dto;

import lombok.Data;

@Data
public class EmailSettingsDTO {


    private String host;
    private Long port;
    private String username;
    private String password;
}
