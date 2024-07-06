package com.joe.cms.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class Address implements Serializable {

    @Column(name = "email")
    private String email;

    @Column(name = "contact")
    private String contact;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "location")
    private String location;

    @Column(name = "regNo")
    private String regNo;

    @Column(name = "postal_address")
    private String postalAddress;
}
