package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeCombination;

import java.util.List;

public interface ProbabilityTypeCombinationDatasourcePort {
    List<String> findByCodeList(List<String> code);
    ProbabilityTypeCombination upsert(ProbabilityTypeCombination probabilityTypeCombination);

    List<ProbabilityTypeCombination> getAllSimpleProbabilityTypeCombination();
}
