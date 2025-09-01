package com.draw_define_combinations.repositories;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.domain.projections.ProbabilityTypeCombinationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProbabilityTypeCombinationRepository extends JpaRepository<ProbabilityTypeCombinationMO, Integer> {
    Optional<ProbabilityTypeCombinationMO> findByCode(String code);

    @Query("SELECT ptc.id, ptc.code FROM ProbabilityTypeCombinationMO ptc")
    List<ProbabilityTypeCombinationProjection> getAllSimpleProbabilityTypeCombination();
}