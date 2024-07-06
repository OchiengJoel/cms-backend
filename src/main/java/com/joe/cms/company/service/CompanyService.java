package com.joe.cms.company.service;

import com.joe.cms.company.model.Company;
import com.joe.cms.company.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public List<Company> findAllCompanies(){
        return companyRepo.findAll();
    }


    public Company getById(Long id){
        return companyRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Company Not Found"));
    }

    public void createCompany(Company company){
        if (isCompanyNameExists(company.getCompanyName())){
            throw new IllegalArgumentException("Company By Similar Name Exists");
        }

        try {
            companyRepo.save(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Saved Successfully" + e.getMessage());
        }
    }

    public void udpdateCompany(Company company){
        if (!companyRepo.existsById(company.getId())){
            throw new IllegalArgumentException("Company Does Not Exist...");
        }

        try {
            companyRepo.save(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Updated Successfully" + e.getMessage());
        }
    }

    public void deleteCompany(Long id){
        companyRepo.deleteById(id);
    }

    private boolean isCompanyNameExists(String companyName) {
        return companyRepo.existsByCompanyName(companyName);
    }
}
