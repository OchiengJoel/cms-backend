package com.joe.cms.parcel.model;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.parcel.enums.ItemType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cms_items")
public class Item extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    /*@Column(name = "weight")
    private double weight;*/

    @ManyToOne
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;


}
