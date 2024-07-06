package com.joe.cms.parcel.model;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.company.model.Company;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cms_weight_bands")
public class WeightBand extends BaseEntity {

    @Column(name = "min_weight")
    private double minWeight;

    @Column(name ="max_weight")
    private double maxWeight;

    @Column(name = "cost")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
