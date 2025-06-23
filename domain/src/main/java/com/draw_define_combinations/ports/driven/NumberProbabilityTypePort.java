package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityType;

import java.util.List;

public interface NumberProbabilityTypePort {
    ProbabilityType getByCode(String code);

    List<ProbabilityType> findAll();
}
