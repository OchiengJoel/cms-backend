package com.joe.cms.parcel.repo;

import com.joe.cms.parcel.model.WeightBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightBandRepo extends JpaRepository<WeightBand, Long> {

    // Query to find the weight band by weight
    WeightBand findByMinWeightLessThanEqualAndMaxWeightGreaterThanEqual(double minWeight, double maxWeight);

    List<WeightBand> findByCompanyId(Long companyId);

    Optional<WeightBand> findByIdAndCompanyId(Long id, Long companyId);

    Optional<WeightBand> findByMinWeightAndMaxWeightAndCompanyId(double minWeight, double maxWeight, Long companyId);
}
