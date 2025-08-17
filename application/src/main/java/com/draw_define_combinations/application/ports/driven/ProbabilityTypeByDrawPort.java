package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeByDraw;

import java.util.List;

public interface ProbabilityTypeByDrawPort {
    List<ProbabilityTypeByDraw> findByProbabilityTypeId(Integer probabilityTypeId);
}
