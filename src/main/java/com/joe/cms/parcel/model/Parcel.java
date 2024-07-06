package com.joe.cms.parcel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.model.Company;
import com.joe.cms.parcel.enums.ParcelStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "cms_parcels")
public class Parcel extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "from_name")
    private String fromName;

    @Column(name = "from_email")
    @Email
    private String fromEmail;

    @Column(name = "from_phone")
    private String fromPhone;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_email")
    @Email
    private String toEmail;

    @Column(name = "to_phone")
    private String toPhone;

    @ManyToOne
    @JoinColumn(name = "from_branch_id")
    private Branch fromBranch;

    @ManyToOne
    @JoinColumn(name = "to_branch_id")
    private Branch toBranch;

    /*@OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL)
    private List<Item> parcelItems;   */

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "date_recorded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRecorded;

    @Column(name = "parcel_cost")
    private double cost;

    @Column(name = "weight")
    private double weight;

    @Column(name = "tracking_reference")
    private String trackingReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParcelStatus status;

    /*@Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParcelStatus status = ParcelStatus.RECORDED; // default status*/

    // Add this method to retrieve the company ID
    public Long getCompanyId() {
        return company != null ? company.getId() : null;
    }

}
