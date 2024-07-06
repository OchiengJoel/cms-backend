package com.joe.cms.company.rest;

import com.joe.cms.company.dto.BranchDTO;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.model.Company;
import com.joe.cms.company.service.BranchService;
import com.joe.cms.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/company/{companyId}/branches")
public class BranchRest {

    private final BranchService branchService;

    private final CompanyService companyService;

    public BranchRest(BranchService branchService, CompanyService companyService) {
        this.branchService = branchService;
        this.companyService = companyService;
    }

  /*  @GetMapping("/list")
    public ResponseEntity<List<Branch>> getAllBranches(@PathVariable Long companyId) {
        List<Branch> branches = branchService.listAllByCompanyId(companyId);
        return new ResponseEntity<>(branches, HttpStatus.OK);
        //return ResponseEntity.ok(branches);
    }*/

    @GetMapping("/list")
    public ResponseEntity<List<BranchDTO>> listBranchesByCompany(@PathVariable Long companyId) {
        List<BranchDTO> branches = branchService.listAllByCompanyId(companyId);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable Long companyId, @PathVariable Long id) {

        Map<String, String> response = new HashMap<>();
        try {
            Branch branch = branchService.findByIdAndCompanyId(id, companyId);
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Company With Id Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createBranch(@PathVariable Long companyId, @RequestBody Branch branch) {
        Company company = companyService.getById(companyId);
        branch.setCompany(company);
        Map<String, String> response = new HashMap<>();
        try {
            branchService.createBranch(branch);
            response.put("message", "Branch created successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
            //return new ResponseEntity<>("Branch created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error Adding New Branch: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable Long companyId, @PathVariable Long id, @RequestBody Branch branch) {

        Company company = companyService.getById(companyId);
        branch.setId(id);
        branch.setCompany(company);
        Map<String, String> response = new HashMap<>();
        try {
            branchService.updateBranch(branch);
            response.put("message", "Branch updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error Editing Branch" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long companyId, @PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            branchService.deleteBranch(id, companyId);
            response.put("message", "Branch Deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
            //return ResponseEntity.ok("Branch deleted successfully");
        } catch (Exception e) {
            response.put("message", "Error Deleting Branch" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /*@DeleteMapping("/delete/batch")
    public ResponseEntity<?> deleteSelectedBranches(@PathVariable Long companyId, @RequestBody List<Long> ids) {
        Map<String, String> response = new HashMap<>();
        try {
            branchService.deleteSelected(ids, companyId);
            response.put("message", "Selected branches deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error Deleting Branch" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping("/delete/batch")
    public ResponseEntity<?> deleteSelectedBranches(@PathVariable Long companyId, @RequestBody List<Long> ids) {
        Map<String, String> response = new HashMap<>();
        try {
            branchService.deleteSelected(ids, companyId);
            response.put("message", "Selected branches deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error Deleting Branches: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



}
