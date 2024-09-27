package com.joe.cms.company.dto;

import com.joe.cms.common.dto.AddressDTO;
import com.joe.cms.projectmanagement.dto.ProjectDTO;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDTO {

    private Long id;
    private String companyName;
    private AddressDTO address;
    private List<ProjectDTO> projects; // Add this field
}
