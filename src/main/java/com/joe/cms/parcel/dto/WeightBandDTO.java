package com.joe.cms.parcel.dto;

import lombok.Data;

@Data
public class WeightBandDTO {

    private Long id;
    private double minWeight;
    private double maxWeight;
    private double cost;
   private Long companyId;
}
