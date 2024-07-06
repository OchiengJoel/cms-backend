package com.joe.cms.company.service;

import com.joe.cms.company.dto.BranchDTO;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.repo.BranchRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private static final String CODE_PREFIX = "BRC";

    private final BranchRepo branchRepo;


    public BranchService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public List<Branch> listAll(){
       return branchRepo.findAll();
    }

    /*public List<Branch> listAllByCompanyId(Long companyId) {
        return branchRepo.findAllByCompanyId(companyId);
    }*/

    public List<BranchDTO> listAllByCompanyId(Long companyId) {
        List<Branch> branches = branchRepo.findAllByCompanyId(companyId);
        return branches.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BranchDTO convertToDTO(Branch branch) {
        BranchDTO dto = new BranchDTO();
        dto.setId(branch.getId());
        dto.setBranchCode(branch.getBranchCode());
        dto.setBranchName(branch.getBranchName());
        dto.setAddress(branch.getAddress());
        return dto;
    }


    public Branch findById(Long id){
        return branchRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch Not Found"));
    }

    public Branch findByIdAndCompanyId(Long id, Long companyId) {
        return branchRepo.findByIdAndCompanyId(id, companyId).orElseThrow(() -> new IllegalArgumentException("Branch not found for the specified company"));
    }

    /*@Transactional
    public void createBranch (Branch branch){
        if (isExistByBranchName(branch.getBranchName())){
            throw new IllegalArgumentException("Branch Name Already Exists");
        }
        if(isExistByBranchCode(branch.getBranchCode())){
            throw new IllegalArgumentException("Branch Code Already Exists");
        }

        // Generate code and set it to the account
        branch.setBranchCode(generateUniqueCode());

        try {
                branchRepo.save(branch);
            } catch (Exception e) {
                throw new IllegalArgumentException("Updated Successfully" + e.getMessage());
            }
    }

    private String generateUniqueCode() {
        String lastCode = branchRepo.findTopByOrderByBranchCodeDesc().map(Branch::getBranchCode).orElse(null);

        // Generate a new code based on the last used code
        if (lastCode != null && lastCode.contains("-")) {
            String[] parts = lastCode.split("-");
            int sequence = Integer.parseInt(parts[1]) + 1;
            return CODE_PREFIX + "-" + String.format("%06d", sequence);
        } else {
            // If no previous code exists or it doesn't have the expected format, start with 1
            return CODE_PREFIX + "-" + String.format("%06d", 1);
        }
    }
*/

    @Transactional
    public void createBranch(Branch branch) {
        if (isExistByBranchName(branch.getBranchName())) {
            throw new IllegalArgumentException("Branch Name Already Exists");
        }
        if (isExistByBranchCode(branch.getBranchCode())) {
            throw new IllegalArgumentException("Branch Code Already Exists");
        }

        // Generate code and set it to the branch
        branch.setBranchCode(generateUniqueCode());

        try {
            branchRepo.save(branch);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Adding New Branch: " + e.getMessage());
        }
    }

    private synchronized String generateUniqueCode() {
        long timestamp = System.currentTimeMillis();
        int randomComponent = (int) (Math.random() * 10000);
        return CODE_PREFIX + "-" + timestamp + "-" + String.format("%04d", randomComponent);
    }

    private boolean isExistByBranchCode(String branchCode) {
        return branchRepo.existsByBranchCode(branchCode);
    }

    private boolean isExistByBranchName(String branchName) {
        return branchRepo.existsByBranchName(branchName);
    }
    @Transactional
    public void updateBranch(Branch branch){
        if (!branchRepo.existsById(branch.getId())){
            throw new IllegalArgumentException("Branch Does Not Exist");
        }
        try {
            branchRepo.save(branch);
        } catch (Exception e) {
            throw new IllegalArgumentException("Updated Successfully" + e.getMessage());
        }
    }


    @Transactional
    public void deleteBranch(Long id, Long companyId){
       /* if (!branchRepo.existsById(id)){
            throw new IllegalArgumentException("Branch Does Not Exist");
        }

        try {
            branchRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        Branch branch = findByIdAndCompanyId(id, companyId);
        try {
            branchRepo.delete(branch);
        } catch (Exception e) {
            throw new IllegalArgumentException("Deleted...." + e.getMessage());
        }
    }

//    @Transactional
//    public void deleteSelected(List<Long> ids, Long companyId){
//        for (Long id: ids){
//            branchRepo.deleteById(id);
//        }
//    }

    @Transactional
    public void deleteSelected(List<Long> ids, Long companyId) {
        for (Long id : ids) {
            deleteBranch(id, companyId);
        }
    }


}
