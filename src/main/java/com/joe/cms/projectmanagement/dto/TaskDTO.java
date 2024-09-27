package com.joe.cms.projectmanagement.dto;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.projectmanagement.enums.TaskStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TaskDTO extends BaseEntity {

    @NotBlank(message = "Cannot Be Empty")
    private String name;

    @NotBlank(message = "Cannot Be Empty")
    private String description;

    private TaskStatus status = TaskStatus.TODO;

    private Date dueDate;

    private Long projectId;
}
