package com.joe.cms.projectmanagement.model;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Company;
import com.joe.cms.projectmanagement.enums.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "cms_project")
public class Project extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "project_start_date")
    @Temporal(TemporalType.DATE)
    private Date projectStartDate;

    @Column(name = "project_end_date")
    @Temporal(TemporalType.DATE)
    private Date projectEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", columnDefinition = "VARCHAR(255) default 'IN_PROGRESS'")
    private ProjectStatus projectStatus = ProjectStatus.IN_PROGRESS;

    @Column(name = "project_location")
    private String projectLocation;

    @Column(name = "project_budget")
    private BigDecimal projectBudget;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();  // Initialize the tasks list

}


//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
//    private List<Task> tasks;
