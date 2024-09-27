package com.joe.cms.common.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AddressDTO {


    private String email;

    private String contact;

    private String country;

    private String city;

    private String location;

    private String regNo;

    private String postalAddress;
}
