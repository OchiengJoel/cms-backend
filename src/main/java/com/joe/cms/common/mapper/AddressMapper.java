package com.joe.cms.common.mapper;

import com.joe.cms.common.Address;
import com.joe.cms.common.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // Convert Address entity to AddressDTO
    public AddressDTO toDTO(Address address) {
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

    // Convert AddressDTO to Address entity
    public Address toEntity(AddressDTO dto) {
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
