package com.joe.cms.company.mapper;

import com.joe.cms.common.Address;
import com.joe.cms.common.dto.AddressDTO;
import com.joe.cms.company.dto.CompanyDTO;
import com.joe.cms.company.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    private CompanyMapper() {
        // private constructor to prevent instantiation
    }
    public static CompanyDTO toDTO(Company company) {
        if (company == null) {
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        dto.setAddress(toAddressDTO(company.getAddress()));
        return dto;
    }

    public static AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO dto = new AddressDTO();
        dto.setEmail(address.getEmail());
        dto.setContact(address.getContact());
        dto.setCountry(address.getCountry());
        dto.setCity(address.getCity());
        dto.setLocation(address.getLocation());
        dto.setRegNo(address.getRegNo());
        dto.setPostalAddress(address.getPostalAddress());
        return dto;
    }

    public static Company toEntity(CompanyDTO dto) {
        if (dto == null) {
            return null;
        }
        Company company = new Company();
        company.setId(dto.getId());
        company.setCompanyName(dto.getCompanyName());
        company.setAddress(toAddressEntity(dto.getAddress()));
        return company;
    }
    public static Address toAddressEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setEmail(dto.getEmail());
        address.setContact(dto.getContact());
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setLocation(dto.getLocation());
        address.setRegNo(dto.getRegNo());
        address.setPostalAddress(dto.getPostalAddress());
        return address;
    }
}
