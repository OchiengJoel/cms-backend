package com.joe.cms.parcel.dto;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.parcel.enums.ItemType;
import lombok.Data;

@Data
public class ItemDTO extends BaseEntity {

    private ItemType itemType;

    private String itemName;

    private String itemDescription;

}
