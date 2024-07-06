package com.joe.cms.parcel.service;

import com.joe.cms.company.repo.CompanyRepo;
import com.joe.cms.parcel.dto.WeightBandDTO;
import com.joe.cms.parcel.model.WeightBand;
import com.joe.cms.parcel.repo.WeightBandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WeightBandService {

    private final WeightBandRepo weightBandRepo;
    private final CompanyRepo companyRepo;

    @Autowired
    public WeightBandService(WeightBandRepo weightBandRepo, CompanyRepo companyRepo) {
        this.weightBandRepo = weightBandRepo;
        this.companyRepo = companyRepo;
    }

    public List<WeightBandDTO> listAllWeightBands(Long companyId) {
        return weightBandRepo.findByCompanyId(companyId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WeightBandDTO findWeightBandById(Long id, Long companyId) {
        WeightBand weightBand = weightBandRepo.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Weight Band Not Found In Records.."));
        return convertToDTO(weightBand);
    }

   /* @Transactional
    public WeightBandDTO createWeightBand(Long companyId, WeightBandDTO weightBandDTO) {
        WeightBand weightBand = convertToEntity(weightBandDTO);
        weightBand.setCompany(companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company Not Found")));
        WeightBand savedWeightBand = weightBandRepo.save(weightBand);
        return convertToDTO(savedWeightBand);
    }*/

    public WeightBandDTO createWeightBand(Long companyId, WeightBandDTO weightBandDTO) {
        Optional<WeightBand> existingWeightBand = weightBandRepo.findByMinWeightAndMaxWeightAndCompanyId(
                weightBandDTO.getMinWeight(), weightBandDTO.getMaxWeight(), companyId);

        if (existingWeightBand.isPresent()) {
            throw new IllegalArgumentException("Weight Band with the same minWeight and maxWeight already exists for this company.");
        }

        WeightBand weightBand = convertToEntity(weightBandDTO);
        weightBand.setCompany(companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company Not Found")));
        WeightBand savedWeightBand = weightBandRepo.save(weightBand);
        return convertToDTO(savedWeightBand);
    }

    public WeightBandDTO updateWeightBand(Long companyId, WeightBandDTO weightBandDTO) {
        WeightBand weightBand = weightBandRepo.findByIdAndCompanyId(weightBandDTO.getId(), companyId)
                .orElseThrow(() -> new IllegalArgumentException("Weight Band Not Found"));
        weightBand.setMinWeight(weightBandDTO.getMinWeight());
        weightBand.setMaxWeight(weightBandDTO.getMaxWeight());
        weightBand.setCost(weightBandDTO.getCost());
        WeightBand updatedWeightBand = weightBandRepo.save(weightBand);
        return convertToDTO(updatedWeightBand);
    }

    public void deleteWeightBand(Long id, Long companyId) {
        WeightBand weightBand = weightBandRepo.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Weight Band Not Found"));
        weightBandRepo.delete(weightBand);
    }

    private WeightBandDTO convertToDTO(WeightBand weightBand) {
        WeightBandDTO weightBandDTO = new WeightBandDTO();
        weightBandDTO.setId(weightBand.getId());
        weightBandDTO.setMinWeight(weightBand.getMinWeight());
        weightBandDTO.setMaxWeight(weightBand.getMaxWeight());
        weightBandDTO.setCost(weightBand.getCost());
        return weightBandDTO;
    }

    private WeightBand convertToEntity(WeightBandDTO weightBandDTO) {
        WeightBand weightBand = new WeightBand();
        weightBand.setId(weightBandDTO.getId());
        weightBand.setMinWeight(weightBandDTO.getMinWeight());
        weightBand.setMaxWeight(weightBandDTO.getMaxWeight());
        weightBand.setCost(weightBandDTO.getCost());
        return weightBand;
    }
}
