package com.joe.cms.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ContactInfo implements Serializable {

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;
}
