package com.joe.cms.company.dto;

import com.joe.cms.common.Address;
import com.joe.cms.common.dto.AddressDTO;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.model.Company;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class BranchDTO {

    private Long id;

    private String branchCode;

    private String branchName;

    //private Address address;

    private AddressDTO address;

    private CompanyDTO company;

    private Long companyId;
}
