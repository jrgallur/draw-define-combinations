package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.DrawProbabilityType;

import java.util.List;

public interface ProbabilityTypePort {
    List<DrawProbabilityType> findByProbabilityTypeId(Short probabilityTypeId);
}
