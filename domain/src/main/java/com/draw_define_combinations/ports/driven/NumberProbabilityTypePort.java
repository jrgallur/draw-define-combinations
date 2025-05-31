package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.NumberProbabilityType;

import java.util.List;

public interface NumberProbabilityTypePort {
    NumberProbabilityType getByCode(String code);

    List<NumberProbabilityType> findAll();
}
