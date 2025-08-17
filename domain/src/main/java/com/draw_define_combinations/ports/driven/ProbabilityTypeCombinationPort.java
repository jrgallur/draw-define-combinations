package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityTypeCombination;

import java.util.List;
import java.util.Optional;

public interface ProbabilityTypeCombinationPort {
    boolean existsByCode(String code);
    ProbabilityTypeCombination upsert(ProbabilityTypeCombination probabilityTypeCombination);

    List<ProbabilityTypeCombination> getAllProbabilityTypeCombinationWithWeightList();

    Optional<ProbabilityTypeCombination> findByCode(String code);
}
