package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityType;

import java.util.List;

public interface ProbabilityTypePort {
    ProbabilityType getByCode(String code);

    List<ProbabilityType> findAll();
}
