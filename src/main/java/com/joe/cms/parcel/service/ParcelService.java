package com.joe.cms.parcel.service;

import com.joe.cms.common.notification.service.NotificationService;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.model.Company;
import com.joe.cms.company.repo.BranchRepo;
import com.joe.cms.company.repo.CompanyRepo;
import com.joe.cms.parcel.enums.ParcelStatus;
import com.joe.cms.parcel.dto.ParcelDTO;
import com.joe.cms.parcel.model.Parcel;
import com.joe.cms.parcel.model.WeightBand;
import com.joe.cms.parcel.repo.ParcelRepo;
import com.joe.cms.parcel.repo.WeightBandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ParcelService {

    private final ParcelRepo parcelRepo;

    private final NotificationService notificationService;

    private final WeightBandRepo weightBandRepo;

    private final CompanyRepo companyRepo;

    private final BranchRepo branchRepo;

    @Autowired
    public ParcelService(ParcelRepo parcelRepo, NotificationService notificationService, WeightBandRepo weightBandRepo, CompanyRepo companyRepo, BranchRepo branchRepo) {
        this.parcelRepo = parcelRepo;
        this.notificationService = notificationService;
        this.weightBandRepo = weightBandRepo;
        this.companyRepo = companyRepo;
        this.branchRepo = branchRepo;
    }

    public List<Parcel> findAllParcelsByCompany(Long companyId) {
        Company company = companyRepo.findById(companyId).orElseThrow(() -> new IllegalArgumentException("Company not found"));
        return company.getParcels();
    }

    public Parcel findById(Long id){
        return parcelRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Parcel Not Found"));
    }

    public Parcel findByIdAndCompanyId(Long id, Long companyId) {
        return parcelRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Parcel Not Found In Records In the Company.."));
    }

    public void createParcel(ParcelDTO parcelDTO, Long companyId) {
        Parcel parcel = convertToEntity(parcelDTO);

        Company company = companyRepo.findById(companyId).orElseThrow(() -> new IllegalArgumentException("Company Not Found"));
        parcel.setCompany(company);

        parcel.setDateRecorded(new Date());
        parcel.setTrackingReference(UUID.randomUUID().toString());
        //parcel.setStatus(ParcelStatus.RECORDED);
        // Set default status only if not provided in DTO
        if (parcel.getStatus() == null) {
            parcel.setStatus(ParcelStatus.RECORDED);
        }

        double totalWeight = parcelDTO.getWeight();
        parcel.setWeight(totalWeight);

        WeightBand weightBand = weightBandRepo.findByMinWeightLessThanEqualAndMaxWeightGreaterThanEqual(totalWeight, totalWeight);
        if (weightBand != null) {
            parcel.setCost(weightBand.getCost());
        } else {
            throw new IllegalArgumentException("WeightBand Not Found For the given weight");
        }
        Parcel savedParcel = parcelRepo.save(parcel);
        notificationService.sendNotification(savedParcel);
    }


    public void updateParcel(ParcelDTO parcelDTO, Long companyId) {
        // Retrieve the existing parcel by its ID
        Parcel existingParcel = parcelRepo.findById(parcelDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Parcel does not exist"));

        // Validate that the parcel belongs to the specified company
        if (!existingParcel.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Parcel does not belong to the specified company");
        }

        // Convert ParcelDTO to existingParcel entity
        convertToEntity(parcelDTO, existingParcel);

        // Calculate total weight and set cost based on weight band
        double totalWeight = parcelDTO.getWeight();
        existingParcel.setWeight(totalWeight);

        WeightBand weightBand = weightBandRepo.findByMinWeightLessThanEqualAndMaxWeightGreaterThanEqual(totalWeight, totalWeight);
        if (weightBand != null) {
            existingParcel.setCost(weightBand.getCost());
        } else {
            throw new IllegalArgumentException("Weight band not found for the given weight");
        }
        // Save the updated parcel
        parcelRepo.save(existingParcel);
    }
    public void deleteParcel(Long id, Long companyId) {
        Parcel parcel = parcelRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Parcel not found"));
        if (!parcel.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Parcel does not belong to the specified company");
        }
        parcelRepo.delete(parcel);
    }


    public void updateParcelStatus(Long id, Long companyId, ParcelStatus status) {
        Parcel parcel = parcelRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Parcel not found"));
        if (!parcel.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Parcel does not belong to the specified company");
        }
        parcel.setStatus(status);
        parcelRepo.save(parcel);
    }

    public ParcelDTO convertToDTO(Parcel parcel) {
        ParcelDTO parcelDTO = new ParcelDTO();
        // Set fields from Parcel to ParcelDTO
        parcelDTO.setId(parcel.getId());
        parcelDTO.setTrackingReference(parcel.getTrackingReference());
        parcelDTO.setFromName(parcel.getFromName());
        parcelDTO.setFromEmail(parcel.getFromEmail());
        parcelDTO.setFromPhone(parcel.getFromPhone());
        parcelDTO.setToName(parcel.getToName());
        parcelDTO.setToEmail(parcel.getToEmail());
        parcelDTO.setToPhone(parcel.getToPhone());
        parcelDTO.setFromBranchId(parcel.getFromBranch() != null ? parcel.getFromBranch().getId() : null);
        parcelDTO.setToBranchId(parcel.getToBranch() != null ? parcel.getToBranch().getId() : null);
        parcelDTO.setItemDescription(parcel.getItemDescription());
        parcelDTO.setWeight(parcel.getWeight());
        parcelDTO.setCost(parcel.getCost());
        parcelDTO.setStatus(parcel.getStatus());
        parcelDTO.setDateRecorded(parcel.getDateRecorded());
        return parcelDTO;
    }

    private Parcel convertToEntity(ParcelDTO parcelDTO) {
        Parcel parcel = new Parcel();
        return convertToEntity(parcelDTO, parcel);
    }

    private Parcel convertToEntity(ParcelDTO parcelDTO, Parcel parcel) {
        // Set fields from ParcelDTO to Parcel entity
        parcel.setFromName(parcelDTO.getFromName());
        parcel.setFromEmail(parcelDTO.getFromEmail());
        parcel.setFromPhone(parcelDTO.getFromPhone());
        parcel.setToName(parcelDTO.getToName());
        parcel.setToEmail(parcelDTO.getToEmail());
        parcel.setToPhone(parcelDTO.getToPhone());
        parcel.setWeight(parcelDTO.getWeight());
        parcel.setCost(parcelDTO.getCost());
        parcel.setItemDescription(parcelDTO.getItemDescription());
        parcel.setDateRecorded(parcelDTO.getDateRecorded());
        parcel.setTrackingReference(parcelDTO.getTrackingReference());
        parcel.setStatus(parcelDTO.getStatus());

        // Set FromBranch and ToBranch if provided
        if (parcelDTO.getFromBranchId() != null) {
            Branch fromBranch = branchRepo.findById(parcelDTO.getFromBranchId()).orElse(null);
            parcel.setFromBranch(fromBranch);
        }
        if (parcelDTO.getToBranchId() != null) {
            Branch toBranch = branchRepo.findById(parcelDTO.getToBranchId()).orElse(null);
            parcel.setToBranch(toBranch);
        }

        return parcel;
    }

}

