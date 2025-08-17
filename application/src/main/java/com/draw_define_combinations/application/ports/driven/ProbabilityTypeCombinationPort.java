package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeCombination;

import java.util.List;
import java.util.Optional;

public interface ProbabilityTypeCombinationPort {
    boolean existsByCode(String code);
    ProbabilityTypeCombination upsert(ProbabilityTypeCombination probabilityTypeCombination);

    List<ProbabilityTypeCombination> getAllProbabilityTypeCombinationWithWeightList();

    Optional<ProbabilityTypeCombination> findByCode(String code);
}
