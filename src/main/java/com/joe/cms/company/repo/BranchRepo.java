package com.joe.cms.company.repo;

import com.joe.cms.company.model.Branch;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {

    boolean existsByBranchName(String branchName);

    boolean existsByBranchCode(String branchCode);

    List<Branch> findAllByCompanyId(Long companyId);

    Optional<Branch> findByIdAndCompanyId(Long id, Long companyId);

    Optional<Branch> findTopByOrderByBranchCodeDesc();
}
