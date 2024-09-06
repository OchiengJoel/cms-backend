package com.joe.cms.projectmanagement.model;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Company;
import com.joe.cms.projectmanagement.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cms_tasks")
public class Task extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
//    private Enum<Status> status;
    private Status status = Status.TODO;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
