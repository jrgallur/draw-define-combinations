package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.ProbabilityTypeCombinationMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProbabilityTypeCombinationRepository extends JpaRepository<ProbabilityTypeCombinationMO, Integer> {
    Optional<ProbabilityTypeCombinationMO> findByCode(String code);

    @Query("SELECT p FROM ProbabilityTypeCombinationMO p LEFT JOIN FETCH p.probabilityTypeCombinationWeightList")
    List<ProbabilityTypeCombinationMO> findAllWithProbabilityTypeCombinationWeightMOList();
}