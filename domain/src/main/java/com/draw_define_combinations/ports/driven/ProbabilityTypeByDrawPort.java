package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.ProbabilityTypeByDraw;

import java.util.List;

public interface ProbabilityTypeByDrawPort {
    List<ProbabilityTypeByDraw> findByProbabilityTypeId(Integer probabilityTypeId);
}
