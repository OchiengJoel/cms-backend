package com.joe.cms.projectmanagement.dto;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.projectmanagement.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskDTO extends BaseEntity {

    @NotBlank(message = "Cannot Be Empty")
    private String description;

    private Status status;

    private Long projectId;
}
