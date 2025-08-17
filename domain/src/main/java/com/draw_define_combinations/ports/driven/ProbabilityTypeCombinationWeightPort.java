package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityTypeCombinationWeight;

import java.util.List;

public interface ProbabilityTypeCombinationWeightPort {
    void saveAll(List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList);
    void deleteByProbabilityTypeCombinationId(Integer probabilityTypeCombinationId);
    }
