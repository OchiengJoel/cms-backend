package com.joe.cms.company.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.joe.cms.common.Address;
import com.joe.cms.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cms_branch")
public class Branch extends BaseEntity {

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_name")
    private String branchName;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
