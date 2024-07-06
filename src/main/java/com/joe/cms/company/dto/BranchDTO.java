package com.joe.cms.company.dto;

import com.joe.cms.common.Address;
import com.joe.cms.company.model.Branch;
import lombok.Data;

@Data
public class BranchDTO {

    private Long id;

    private String branchCode;

    private String branchName;

    private Address address;



}
