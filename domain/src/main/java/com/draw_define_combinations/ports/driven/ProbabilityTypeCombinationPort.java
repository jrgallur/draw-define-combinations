package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityTypeCombination;

import java.util.List;

public interface ProbabilityTypeCombinationPort {
    boolean existsByCode(String code);
    void upsert(ProbabilityTypeCombination probabilityTypeCombination);

    List<ProbabilityTypeCombination> getAllProbabilityTypeCombinationList();
}
