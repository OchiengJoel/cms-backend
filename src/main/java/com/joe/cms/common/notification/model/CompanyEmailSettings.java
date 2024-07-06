package com.joe.cms.common.notification.model;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Company;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name = "cms_email")
public class CompanyEmailSettings extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private String host;
    private Long port;
    private String username;
    private String password;

}
