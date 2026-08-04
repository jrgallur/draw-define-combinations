package com.draw_define_combinations.repositories;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.domain.projections.ProbabilityTypeCombinationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProbabilityTypeCombinationRepository extends JpaRepository<ProbabilityTypeCombinationMO, Integer> {
    @Query("SELECT ptc.id AS id, ptc.code AS code FROM ProbabilityTypeCombinationMO ptc")
    List<ProbabilityTypeCombinationProjection> getAllSimpleProbabilityTypeCombination();

    List<ProbabilityTypeCombinationMO> findAllByCodeIn(Collection<String> codes);
}