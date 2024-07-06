package com.joe.cms.parcel.rest;

import com.joe.cms.company.model.Branch;
import com.joe.cms.parcel.enums.ParcelStatus;
import com.joe.cms.parcel.dto.ParcelDTO;
import com.joe.cms.parcel.model.Parcel;
import com.joe.cms.parcel.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/company/{companyId}/parcels")
public class ParcelRestController {


    private final ParcelService parcelService;

    @Autowired
    public ParcelRestController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<?>> getAllParcels(@PathVariable Long companyId) {
        List<ParcelDTO> parcels = parcelService.findAllParcelsByCompany(companyId)
                .stream()
                .map(parcelService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParcelById(@PathVariable Long companyId, @PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            Parcel parcel = parcelService.findByIdAndCompanyId(id, companyId);
            return new ResponseEntity<>(parcel, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Company With Id Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createParcel(@PathVariable Long companyId, @RequestBody @Valid ParcelDTO parcelDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            parcelService.createParcel(parcelDTO, companyId);
            response.put("message", "Parcel Created Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage()); // Propagate specific error messages
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("message", "Error Creating New Parcel");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateParcel(@PathVariable Long companyId, @PathVariable Long id, @RequestBody ParcelDTO parcelDTO) {

        Map<String, String> response = new HashMap<>();
        if (!id.equals(parcelDTO.getId())) {
            response.put("message", "Parcel ID mismatch");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            parcelService.updateParcel(parcelDTO, companyId);
            response.put("message", "Parcel Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error Updating Parcel.." + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteParcel(@PathVariable Long companyId, @PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            parcelService.deleteParcel(id, companyId);
            response.put("message", "Parcel Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("message", "Error Deleting Parcel.." + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateParcelStatus(@PathVariable Long companyId, @PathVariable Long id, @RequestParam ParcelStatus status) {

        Map<String, String> response = new HashMap<>();
        try {
            parcelService.updateParcelStatus(id, companyId, status);
            response.put("message", "Status Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error Updating Status.." + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
