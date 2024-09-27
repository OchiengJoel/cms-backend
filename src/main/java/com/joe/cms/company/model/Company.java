package com.joe.cms.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.joe.cms.common.Address;
import com.joe.cms.common.BaseEntity;
import com.joe.cms.parcel.model.Parcel;
import com.joe.cms.parcel.model.WeightBand;
import com.joe.cms.projectmanagement.model.Project;
import com.joe.cms.projectmanagement.model.Task;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "cms_company")
@JsonIgnoreProperties({"parcels", "branches", "weightBands"})
public class Company extends BaseEntity {

    @Column(name = "company_name")
    private String companyName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Parcel> parcels;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Branch> branches;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<WeightBand> weightBands;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore // This will prevent infinite recursion
    private List<Project> projects;

}
