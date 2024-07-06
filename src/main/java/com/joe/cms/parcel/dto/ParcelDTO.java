package com.joe.cms.parcel.dto;

import com.joe.cms.common.BaseEntity;
import com.joe.cms.parcel.enums.ParcelStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class ParcelDTO extends BaseEntity {

    @NotBlank(message = "From name is mandatory")
    private String fromName;

    @Email(message = "From email should be valid")
    private String fromEmail;

    @NotBlank(message = "From phone is mandatory")
    private String fromPhone;

    @NotBlank(message = "To name is mandatory")
    private String toName;

    @Email(message = "To email should be valid")
    private String toEmail;

    @NotBlank(message = "To phone is mandatory")
    private String toPhone;

   @NotNull(message = "From branch ID is mandatory")
    private Long fromBranchId;

  @NotNull(message = "To branch ID is mandatory")
    private Long toBranchId;

    /*@NotEmpty(message = "Parcel item list cannot be empty")
    private List<ItemDTO> parcelItem;*/

    @NotNull(message = "Item description is mandatory")
    private String itemDescription;

    @Positive(message = "Weight must be positive")
    private double weight;

    //@Positive(message = "Cost must be positive")
    private double cost;

    @NotNull(message = "Date recorded is mandatory")
    private Date dateRecorded;

    private String trackingReference;

    private ParcelStatus status;

}
