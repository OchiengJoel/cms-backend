package com.joe.cms.projectmanagement.dto;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.projectmanagement.enums.ProjectStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProjectDTO extends BaseEntity {

    @NotBlank(message = "Cannot Be Empty")
    private String name;

    @NotBlank(message = "Cannot Be Empty")
    private String description;

    private Long companyId; // Refers to the company the project belongs to

    private List<Long> taskIds; // List of task IDs associated with the project

    private Date projectStartDate;
    private Date projectEndDate;
    private ProjectStatus projectStatus = ProjectStatus.IN_PROGRESS;
    private String projectLocation;
    private BigDecimal projectBudget;



}
