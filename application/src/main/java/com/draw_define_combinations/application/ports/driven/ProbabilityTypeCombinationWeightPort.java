package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeight;

import java.util.List;

public interface ProbabilityTypeCombinationWeightPort {
    void saveAll(List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList);
    void deleteByProbabilityTypeCombinationId(Integer probabilityTypeCombinationId);
    }
