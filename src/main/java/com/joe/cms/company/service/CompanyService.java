package com.joe.cms.company.service;

import com.joe.cms.company.dto.CompanyDTO;
import com.joe.cms.company.mapper.CompanyMapper;
import com.joe.cms.company.model.Company;
import com.joe.cms.company.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepo companyRepo, CompanyMapper companyMapper) {
        this.companyRepo = companyRepo;
        this.companyMapper = companyMapper;
    }

    public List<CompanyDTO> findAllCompanies() {
        return companyRepo.findAll().stream()
                .map(CompanyMapper::toDTO) // Use static method reference here
                .collect(Collectors.toList());
    }

    public CompanyDTO getById(Long id) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company Not Found"));
        return CompanyMapper.toDTO(company); // Use static method reference here
    }

    public void createCompany(CompanyDTO companyDTO) {
        if (companyRepo.existsByCompanyName(companyDTO.getCompanyName())) {
            throw new IllegalArgumentException("Company By Similar Name Exists");
        }

        Company company = CompanyMapper.toEntity(companyDTO); // Use static method reference here
        companyRepo.save(company);
    }

    public void updateCompany(CompanyDTO companyDTO) {
        if (!companyRepo.existsById(companyDTO.getId())) {
            throw new IllegalArgumentException("Company Does Not Exist...");
        }

        Company company = CompanyMapper.toEntity(companyDTO); // Use static method reference here
        companyRepo.save(company);
    }

    public void deleteCompany(Long id) {
        if (!companyRepo.existsById(id)) {
            throw new IllegalArgumentException("Company Not Found");
        }
        companyRepo.deleteById(id);
    }


    private boolean isCompanyNameExists(String companyName) {
        return companyRepo.existsByCompanyName(companyName);
    }
}
