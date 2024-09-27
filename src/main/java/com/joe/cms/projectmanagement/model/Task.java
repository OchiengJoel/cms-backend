package com.joe.cms.projectmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Company;
import com.joe.cms.projectmanagement.enums.TaskStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "cms_tasks")
public class Task extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @Enumerated(EnumType.STRING)
//    private Enum<Status> status;
    private TaskStatus status = TaskStatus.TODO;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore // Avoid recursion in JSON responses
    private Project project;
}
