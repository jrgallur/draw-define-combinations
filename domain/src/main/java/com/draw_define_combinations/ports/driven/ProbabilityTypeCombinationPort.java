package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityTypeCombination;

public interface ProbabilityTypeCombinationPort {
    boolean existsByCode(String code);
    void upsert(ProbabilityTypeCombination probabilityTypeCombination);
}
