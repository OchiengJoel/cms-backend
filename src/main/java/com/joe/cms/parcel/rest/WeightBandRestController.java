package com.joe.cms.parcel.rest;

import com.joe.cms.parcel.dto.WeightBandDTO;
import com.joe.cms.parcel.service.WeightBandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/company/{companyId}/weightbands")
public class WeightBandRestController {

    private final WeightBandService weightBandService;

    @Autowired
    public WeightBandRestController(WeightBandService weightBandService) {
        this.weightBandService = weightBandService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllWeightBands(@PathVariable Long companyId) {
        try {
            List<WeightBandDTO> weightBands = weightBandService.listAllWeightBands(companyId);
            return ResponseEntity.ok(weightBands);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving weight bands: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findWeightBandById(@PathVariable Long id, @PathVariable Long companyId) {
        Map<String,String> response = new HashMap<>();
        try {
            WeightBandDTO weightBand = weightBandService.findWeightBandById(id, companyId);
            response.put("message", "This is the list...");
            return new ResponseEntity<>(response, HttpStatus.OK);
           // return ResponseEntity.ok(weightBand);
        } catch (IllegalArgumentException e) {
            response.put("message", "Weight band not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error retrieving weight band: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWeightBand(@PathVariable Long companyId, @RequestBody WeightBandDTO weightBandDTO) {

        Map<String, String> response = new HashMap<>();
        try {
            WeightBandDTO createdWeightBand = weightBandService.createWeightBand(companyId, weightBandDTO);
            response.put("message", "WeightBand Added Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
           // return ResponseEntity.status(201).body(createdWeightBand);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid Input.." + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           // return ResponseEntity.status(400).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error creating weight band:" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(500).body("Error creating weight band: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWeightBand(@PathVariable Long companyId, @PathVariable Long id, @RequestBody WeightBandDTO weightBandDTO) {

        Map<String, String> response = new HashMap<>();
        try {
            weightBandDTO.setId(id);
            WeightBandDTO updatedWeightBand = weightBandService.updateWeightBand(companyId, weightBandDTO);
            response.put("message", "Weight band updated successfully" + updatedWeightBand.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
            /*response.put("message", "WeightBand Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);*/
           // return ResponseEntity.ok(updatedWeightBand);
        } catch (IllegalArgumentException e) {
            response.put("message", "Weight band not found:" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(404).body("Weight band not found: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error updating weight band:" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           // return ResponseEntity.status(500).body("Error updating weight band: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWeightBand(@PathVariable Long companyId, @PathVariable Long id) {

        Map<String, String> response = new HashMap<>();
        try {
            weightBandService.deleteWeightBand(id, companyId);
            response.put("message", "Weight band deleted successfully..");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            //return ResponseEntity.status(204).body("Weight band deleted successfully");
        } catch (IllegalArgumentException e) {
            response.put("message", "Weight band not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(404).body("Weight band not found: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error deleting weight band: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(500).body("Error deleting weight band: " + e.getMessage());
        }
    }
}
